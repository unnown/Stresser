package com.frontiers.Stresser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.PlayerList;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import java.util.concurrent.CopyOnWriteArrayList;

public class ACManager extends JavaPlugin implements Listener
{
    private static JavaPlugin plugin;
	public static Logger log;
	  
	private TPSCheck tpsCheck = new TPSCheck();
	private boolean toggle = false;	  

    @Override
    public void onEnable() {
	    setPlugin(this);
	    log = getLogger();
	    saveDefaultConfig();    	
    	
        getServer().getScheduler().scheduleSyncRepeatingTask(this, tpsCheck, 20, 20);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }	
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (command.getName().equalsIgnoreCase("spawnbot")) {
            int range = 2000;
            int num = 1;
            if (args.length > 0) {
                num = Integer.parseInt(args[0]);
            }
            if (args.length > 1) {
                range = Integer.parseInt(args[1]);
            }

            for (int i = 0; i < num; i++) {
            	
            	try {
            		
	                Random random = new Random();
	                String name = ChatColor.BLUE + "Bot" + random.nextInt(1000) + i;
	                WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
	                
	                PlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();
	                UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
	                GameProfile gameProfile = new GameProfile(uuid, name);
	
	                EntityPlayer entityplayer = new EntityPlayer(playerList.getServer(), world, gameProfile, new PlayerInteractManager(world));
	                new DummyPlayerConnection(playerList.getServer(), new DummyNetworkManager(), entityplayer);
	
	                entityplayer.spawnIn(world);
	                entityplayer.playerInteractManager.a((WorldServer) entityplayer.world);
	                entityplayer.playerInteractManager.b(world.getWorldData().getGameType());
	
	                entityplayer.setPosition(random.nextInt(range * 2) - range, 100, random.nextInt(range * 2) - range);
	
	                playerList.players.add(entityplayer);
	                world.addEntity(entityplayer);
	                playerList.a(entityplayer, world);
	
	                sender.sendMessage("Added player " + entityplayer.getName() + ChatColor.RESET + " at " + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ".");
	                TimeUnit.MILLISECONDS.sleep(200); //1 tick
	                
            	} catch (Exception ex) {
            		log.log(Level.WARNING, ex.getMessage());
            	}
            	
            }

            sender.sendMessage("Added " + num + " bots!");
            
            return true;
        }

        if (command.getName().equalsIgnoreCase("killbots")) {
            PlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();
            for (EntityPlayer entityplayer : (CopyOnWriteArrayList<EntityPlayer>) playerList.players) {
                if (entityplayer.getName().startsWith(ChatColor.BLUE + "Bot")) {
                    entityplayer.playerConnection.disconnect("");
                    sender.sendMessage("Disconnected " + entityplayer.getName());
                }
            }
        }

        if (command.getName().equalsIgnoreCase("debug")) {
            toggle = !toggle;
            float tps = 0;
            for (Long l : tpsCheck.history) {
                if (l != null)
                    tps += 20 / (l / 1000);
            }
            tps = tps / tpsCheck.history.size();

            sender.sendMessage("TPS: " + tps + " Loaded chunks: " + Bukkit.getWorlds().get(0).getLoadedChunks().length + " Entities: " + Bukkit.getWorlds().get(0).getEntities().size());
        }

        if (command.getName().equalsIgnoreCase("testTimers")) {
        	
            int time = 60 * 1000;
            int delay = 50;
            
            if (args.length > 0) {
            	time = Integer.parseInt(args[0]);
            }
        	
            if (args.length > 1) {
            	delay = Integer.parseInt(args[1]);
            }            
            
        	long StartTime = System.currentTimeMillis();
    		HashMap<Integer, LogThreads> curTasks = new HashMap<Integer, LogThreads>();
    		List<Integer> tmpTasks = new ArrayList<Integer>();
    		  
    		while (System.currentTimeMillis() < (StartTime + time)) {
    			try {
    				List<Integer> newtmpTasks = new ArrayList<Integer>(); // make a new list for the next loop
    				  
    				List<BukkitWorker> workers = Bukkit.getScheduler().getActiveWorkers();
    				List<BukkitTask> tasks = Bukkit.getScheduler().getPendingTasks();
    				  
    				for (BukkitWorker work : workers) {
    					if (!curTasks.isEmpty()) {
    						  
    						if (tmpTasks.contains(work.getTaskId())) {	
    							  
    							tmpTasks.remove(work.getTaskId());  // this task is still running, remove it since we don't need to set it's end time
    							  
    						} else {
    							  
    							  //this is a new task
    							for (BukkitTask tsk : tasks) {
    								  
    								if (work.getTaskId() == tsk.getTaskId()) {		
    									  
    									curTasks.put(tsk.getTaskId(), new LogThreads(System.currentTimeMillis(), tsk));	
    									break;
    									  
    								}
    							}
    							  
    						}
    						newtmpTasks.add(work.getTaskId());
    						  
    					} else {
    						  
    						// these were running before we started this check, hence the 0 start time
    						for (BukkitTask tsk : tasks) {
    							  
    							if (work.getTaskId() == tsk.getTaskId()) {
    								  
    								curTasks.put(tsk.getTaskId(), new LogThreads(0, tsk));
    								newtmpTasks.add(tsk.getTaskId());
    								break;
    								  
    							}
    							  
    						}
    						  
    					}
    				}				  
    				  
    				//tmpTasks should only contain tasks that are not in a worker anymore (old tasks which are ended, tasks that are still running won't be removed)
    				for (Integer taskID : tmpTasks) {
    					curTasks.get(taskID).setEnd(System.currentTimeMillis());
    				}
    				  
    				tmpTasks = newtmpTasks; //and put them back in the list
    				TimeUnit.MILLISECONDS.sleep(delay); //1 tick
    				  
    			} catch (Exception ex) {
    				log.log(Level.WARNING, ex.getMessage());
    			}
    			  
    			// Check if we found some nice things
    			if (!curTasks.isEmpty()) {
    				try {
    					  
    					PrintWriter writer = new PrintWriter("timer.csv", "UTF-8"); // create an csv file and save it all
    					   
    					for (LogThreads logs : curTasks.values()) {
    						writer.println(logs.task.getTaskId() + ";" + logs.task.getOwner().getName() + ";" + logs.task.isSync() + ";" + logs.startTime + ";" + logs.EndTime + ";");
    					}
    					    
    					writer.close();
    					
    					sender.sendMessage("Saved timers to file");
    					
    				} catch (Exception ex) {
    					log.log(Level.WARNING, ex.getMessage());
    				}			  
    			} else {
    				sender.sendMessage("No tasks found?");
    			}
    		}	  
        	return true;
        }
        
        return false;
    }
    
	public static String color(String s)
	{
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
	  
	public static JavaPlugin getPlugin()
	{
	    return plugin;
	}
	  
	public static void setPlugin(JavaPlugin plugin)
	{
		ACManager.plugin = plugin;
	}	  
}
