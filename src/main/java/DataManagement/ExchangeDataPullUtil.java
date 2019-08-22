package main.java.DataManagement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.Constants;
import main.GEInterface;
import main.Inventory;
import main.java.DataStructures.ItemExchangeInfo;
import main.java.DataStructures.PurchasableItem;
import main.java.DataStructures.TradingItem;
import main.java.DataStructures.WindowState;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.nvalue.amnv.differences.D;
import org.chocosolver.solver.variables.IntVar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class ExchangeDataPullUtil {

    public Map<Integer, ItemExchangeInfo> allItems;
    private List<PurchasableItem> viableItems;

    public List<TradingItem> optimalItems;

    private static ExchangeDataPullUtil instance;

    private ExchangeDataPullUtil() {
    }

    public static ExchangeDataPullUtil getInstance() {
        if (instance == null) {
            instance = new ExchangeDataPullUtil();
        }
        return instance;
    }

    public void Initialize() {
        allItems = new LinkedHashMap<>();
        viableItems = new ArrayList<>();
        optimalItems = new ArrayList<>();

        ParseFromURLToMap();
        // FilterAllForViable(Inventory.getInstance().getCoins(), Constants.minProfit);
    }

    public void FilterAllForViable(int coins, int minimumProfit) {
        viableItems.clear();
        for (ItemExchangeInfo item : allItems.values()) {
            if (item.isViable(coins, minimumProfit)) {
                System.out.println("Viable");
                PurchasableItem viable = new PurchasableItem(item);
                viableItems.add(viable);
            }
        }
        System.out.println("Items Filtered");
    }

    public PurchasableItem GetViableItem(String name) {
        for (PurchasableItem item : viableItems) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public TradingItem[] getOptimal() {
        CalculateOptimalItems(Inventory.getInstance().getCoins(), GEInterface.getInstance().getWindowStateCount(WindowState.Empty), Constants.minProfit);
        return optimalItems.toArray(new TradingItem[0]);
    }

    public void CalculateOptimalItems(int goldAvailable, int windowsOpen, int minimumProfit) {
        Model model = new Model("Optimal Item Finder");

        if (optimalItems.size() > 0) {
            optimalItems.clear();
        }

        FilterAllForViable(goldAvailable, minimumProfit);
        System.out.println(viableItems.size());


        IntVar[] variables = new IntVar[viableItems.size()];
        int[] itemCosts = new int[viableItems.size()];
        int[] itemProfits = new int[viableItems.size()];
        for (int i = 0; i < viableItems.size(); i++) {
            itemCosts[i] = viableItems.get(i).getBuyPrice();
            itemProfits[i] = viableItems.get(i).getMargin();
        }
        for (int i = 0; i < viableItems.size(); i++) {
            System.out.println(viableItems.get(i).toString());
            variables[i] = model.intVar(0, viableItems.get(i).getBuyLimit());
            variables[i].mul(itemProfits[i]).ge(minimumProfit).or(variables[i].eq(0)).post();
        }
        IntVar costMax = model.intVar(0, goldAvailable);
        IntVar profit = model.intVar(IntVar.MIN_INT_BOUND, IntVar.MAX_INT_BOUND);

        model.knapsack(variables, costMax, profit, itemCosts, itemProfits).post();

        IntVar nonPurchasedItemCount = model.intVar(0, viableItems.size());

        model.among(nonPurchasedItemCount, variables, new int[]{0}).post();
        model.arithm(nonPurchasedItemCount, ">=", viableItems.size() - windowsOpen).post();

        model.setObjective(Model.MAXIMIZE, profit);

        Solver solver = model.getSolver();
        System.out.println("Items Filtered");


        int profitSum = 0;
        int costSum = 0;
        while (solver.solve()) {
            profitSum = 0;
            costSum = 0;
            int windowNum = 0;
            optimalItems.clear();
            for (int i = 0; i < variables.length; i++) {
                profitSum += variables[i].getValue() * itemProfits[i];
                costSum += variables[i].getValue() * itemCosts[i];

                if (variables[i].getValue() > 0) {
                    optimalItems.add(new TradingItem(viableItems.get(i).getName(), variables[i].getValue(), viableItems.get(i).getBuyPrice(), viableItems.get(i).getSellPrice()));
                    windowNum++;
                }
            }

        }

        System.out.println("Profit " + profitSum);
        System.out.println("Cost : " + costSum);
    }

    public void ParseFromURLToMap() {
        try {
            String itemData = cleanJson(getJsonStringFromURL(Constants.osBuddyAPIURL));
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<ItemExchangeInfo> myObjects = mapper.readValue(cleanJson(itemData), new TypeReference<List<ItemExchangeInfo>>() {});
            myObjects = AddBuySpriteAndBuyLimits(myObjects);
            PopulateMapWithItemsList(myObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getJsonStringFromURL(String urlString) {
        StringBuilder builder = new StringBuilder("");
        try {
            URL url = new URL(urlString);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            while (null != (str = br.readLine())) {
                builder.append(str);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }


    private static String cleanJson(String input) {
        input = input.replace("\\u0027", "\'").replace("\\u0026", "&");
        String output = input.substring(1, input.length() - 1);
        output = "[" + output + "]";
        output = output.replaceAll("\"\\d+\":", "");

        return output;
    }

    private List<ItemExchangeInfo> AddBuySpriteAndBuyLimits(List<ItemExchangeInfo> items) {
        items.sort(new Comparator<ItemExchangeInfo>() {
            @Override
            public int compare(ItemExchangeInfo o1, ItemExchangeInfo o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        File file = new File(Constants.buyLimitsFile);
        try {
            Scanner s = new Scanner(file);
            int currentID = 0;
            int currentBuyLimit = 0;
            for (ItemExchangeInfo item : items) {
                if (s.hasNextInt()) {

                    while (currentID != item.getId() && currentID < item.getId()) {
                        currentID = s.nextInt();
                        currentBuyLimit = s.nextInt();
                    }
                    if (currentID == item.getId()) {
                        item.setBuyLimit(currentBuyLimit);
                    }
                    File sprite = new File("src/main/resources/items-icons/" + item.getId() + ".png");
                    if (sprite.exists()) {
                        item.setSprite(ImageIO.read(sprite));
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return items;
    }
    private void PopulateMapWithItemsList(List<ItemExchangeInfo> items){
        for (ItemExchangeInfo item: items) {
            allItems.put(item.getId(), item);
        }
    }


}