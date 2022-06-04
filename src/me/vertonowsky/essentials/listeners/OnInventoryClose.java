package me.vertonowsky.essentials.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OnInventoryClose implements Listener {

    //public static NPC npc;
    //private Map<User, BukkitTask> odliczanieCooldownu = new HashMap<User, BukkitTask>();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        /*Player p = (Player) e.getPlayer();
        User pUUID = User.get(p.getUniqueId());
        Inventory inv = e.getInventory();

        if (e.getInventory().getName().equalsIgnoreCase("§8Konsola")) {
            ArrayList<ItemStack> items = new ArrayList<>();

            for (int slot = 0; slot < inv.getSize()-9; slot++) {
                if (inv.getItem(slot) != null) items.add(inv.getItem(slot));
            }

            p.sendMessage("§a§lSukces: §7Kod zawiera §b" + items.size() + " §7poleceń.");


            ItemStack itemStack = Methods.createItem(new ItemStack(Material.ARROW, 1), "§aFORWARD");


            BukkitTask loopId = new BukkitRunnable() {
                @Override
                public void run() {
                    int loop = 0;

                    if (loop < items.size()) {
                        if (items.get(loop).isSimilar(itemStack)) {
                            p.sendMessage(loop + "");

                            OnBlockPlace.goForward(OnBlockPlace.robotLoc);
                            loop++;
                        }
                    }

                    if (loop == items.size()) {
                        cancel();
                        odliczanieCooldownu.remove(pUUID);
                    }

                }
            }.runTaskTimerAsynchronously(Main.getInst(), 20, 20);

            odliczanieCooldownu.put(pUUID, loopId);




            //FallingBlock block = p.getWorld().spawnFallingBlock(loc2, Material.CHEST, (byte) 0);




            /*final boolean fixhead = false;
            final boolean tablist = true;

            npc = new NPC("name", loc, Main.getInst());



            npc.setRecipientType(NPC.Recipient.LISTED_RECIPIENTS); //EDIT THE PACKET RECEIVERS
            npc.addRecipient(p); //ADDING PLAYER TO PACKET RECEIVERS

            npc.spawn(tablist, fixhead);


            Vector from = new Vector(loc.getX(), loc.getY(), loc.getZ());
            Vector to  = new Vector(loc2.getX(), loc2.getY(), loc2.getZ());

            Vector vector = to.subtract(from);




            try {
                npc.rotateHead(p.getLocation().getPitch(), p.getLocation().getYaw());
                npc.setDisplayNameAboveHead("§8Zadanie 1");
                //npc.move(vector, true);
                //npc.camera(false, p);

            } catch (IOException exe) {
                exe.printStackTrace();
            }

            p.sendMessage("" + npc.getEntityId());


        }

         */

    }


}
