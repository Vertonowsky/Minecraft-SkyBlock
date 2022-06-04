package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.API;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class OnSignChange implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();

        /*if (e.getLine(0).equalsIgnoreCase("[SKLEP]")) {
            if (!p.hasPermission("vert.*")) {
                p.sendMessage("§c§lBłąd: §7Nie masz uprawnień do tworzenia sklepu.");
                return;
            }


            if (ASkyBlockAPI.getInstance().isCoop(p)) {
                p.sendMessage("§c§lBłąd: §7Nie możesz utworzyć sklepu gdy posiadasz uprawnienia do kilku wysp.");
                return;
            }

            if (ASkyBlockAPI.getInstance().playerIsOnIsland(p)) {
                if (e.getLine(0).equalsIgnoreCase("[SKLEP]")) {
                    Double price;
                    int id = 0;
                    short data = 0;
                    int amount = 1;
                    int shopSize = 1;


                    /*
                    Line 2


                    String line2 = e.getLine(1);
                    line2 = line2.replaceAll("\\$", "");
                    if (API.isDouble(line2)) {
                        price = Double.parseDouble(line2);
                    } else {
                        e.setLine(0, "§4[SKLEP]");
                        p.sendMessage("§c§lBłąd §8- §fLinia 2§7: Błędna wartość: \"§f" + e.getLine(1) + "§7\". Prawidłowy przyklad: \"§f19.99§7\".");
                        return;
                    }


                    /*
                        Line 3

                        Amount - ID - Data - ShopSize

                    int[] returnedData = getItemDetailsFromString(e.getLine(2));
                    amount = returnedData[0];
                    id = returnedData[1];
                    data = Short.parseShort(returnedData[2] + "");
                    shopSize = returnedData[3];


                    if (shopSize % amount != 0) {
                        e.setLine(0, "§4[SKLEP]");
                        p.sendMessage("§c§lBłąd §8- §fLinia 3§7: Nieprawidłowa ilość sztuk w sklepie: \"§f" + shopSize + "§7\".");
                        return;
                    }

                    if (id == 0 || amount == 0) {
                        e.setLine(0, "§4[SKLEP]");
                        p.sendMessage("§c§lBłąd §8- §fLinia 3§7: Błędna wartość: \"§f" + e.getLine(2) + "§7\". Prawidłowy przyklad: \"§f1 4 : 1§7\".");
                        p.sendMessage("§6§lInfo: §7Wzór §8[§f<ilość> <ID>,[<data>]§8]§7.");
                        return;
                    }


                    String data2 = "";
                    if (data != 0) data2 = "," + data;
                    double priceRounded = new BigDecimal(price.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();


                    ItemStack item = new ItemStack(Material.getMaterial(id), 1, data);
                    if (InventoryAPI.getItemAmountInInventory(p, item, false) >= shopSize) {
                        InventoryAPI.removeItemFromInventory(p, item, shopSize);
                    } else {
                        e.setLine(0, "§4[SKLEP]");
                        p.sendMessage("§c§lBłąd: §7Nie posiadasz przy sobie tylu przedmiotów! §f[" + shopSize + "]§7.");
                        return;
                    }


                    e.setLine(0, "§2[SKLEP]");
                    e.setLine(1, "$" + priceRounded + ":0");
                    e.setLine(2, amount + " " + id + data2 + ":" + shopSize);
                    e.setLine(3, "§7" + p.getName());

                    p.sendMessage("§a§lSukces: §7Pomyślnie utworzono sklep.");
                    return;

                }
            }
        }
        */

    }










    public static int[] getItemDetailsFromString(String line) {
        /*
            Amount - ID - Data - ShopSize
         */

        int[] toReturn = new int[4];

        String[] line3 = line.split(":");
        if (line3.length == 2) {
            line3[1] = line3[1].replaceAll(" ", "");
            if (API.isInt(line3[1])) toReturn[3] = Integer.parseInt(line3[1]);
        }



        String[] halfLine3 = line3[0].split(" ");
        if (halfLine3.length == 2) {
            if (API.isInt(halfLine3[0])) toReturn[0] = Integer.parseInt(halfLine3[0]);
        }


        if (halfLine3.length > 1) {
            String[] quarterLine3 = halfLine3[1].split(",");
            if (quarterLine3.length == 2) {
                if (API.isShort(quarterLine3[1])) toReturn[2] = Short.parseShort(quarterLine3[1]);
            }

            if (API.isInt(quarterLine3[0])) toReturn[1] = Integer.parseInt(quarterLine3[0]);
        }

        if (toReturn[3] == 0) toReturn[3] = toReturn[0];

        return toReturn;

    }
}
