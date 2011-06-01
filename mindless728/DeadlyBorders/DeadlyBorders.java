package mindless728.DeadlyBorders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.Scanner;

public class DeadlyBorders extends JavaPlugin implements CommandExecutor {
	HashMap<World, WorldConfig> borders;
	WorldConfig defaultBorder;
	String configFile;

	public DeadlyBorders() {
		borders = new HashMap<World, WorldConfig>();
		defaultBorder = new WorldConfig(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public void onEnable() {
		configFile = getDataFolder().getPath()+File.separatorChar+"DeadlyBorders.txt";
		loadConfig();

		System.out.println(getDescription().getName()+" version "+getDescription().getVersion()+" enabled");
	}

	public void onDisable() {
		System.out.println(getDescription().getName()+" version "+getDescription().getVersion()+" disabled");
	}

	public boolean onCommand(CommandSender cs, Command cmd, String str, String[] strs) {
		return true;
	}

	public void loadConfig() {
		String s;
		World w;
		WorldConfig wc = null;
		Scanner scanner;

		try {
			scanner = new Scanner(new File(configFile));
			while(scanner.hasNext()) {
				s = scanner.next(); //get the data name
				if(s.equals("World:")) {
					s = scanner.next(); //gets the name of the world
					if(s.equals("Default")) //check for default config
						wc = defaultBorder;
					else
						wc = new WorldConfig();
					wc.worldName = s;
				} else if(s.equals("Min-X:")) {
					if(wc == null)
						continue;
					wc.minX = scanner.nextInt();
				} else if(s.equals("Max-X:")) {
					if(wc == null)
						continue;
					wc.maxX = scanner.nextInt();
				} else if(s.equals("Min-Z:")) {
					if(wc == null)
						continue;
					wc.minZ = scanner.nextInt();
				} else if(s.equals("Max-Z:")) {
					if(wc == null)
						continue;
					wc.maxZ = scanner.nextInt();
				} else if(s.equals("EndWorld")) {
					if(wc == null)
						continue;
					if(wc.worldName.equals("Default"))
						continue;
					w = getServer().getWorld(wc.worldName);
					borders.put(w, wc);
				}
			}
		} catch(FileNotFoundException fnfe) {
			saveConfig();
		} catch(Exception e) {
			//@TODO output error message here
		}
	}

	public void saveConfig() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			WorldConfig wc = null;
			for(World w : borders.keySet()) {
				wc = borders.get(w);
				writer.write("World: "+wc.worldName);
				writer.newLine();
				writer.write("Min-X: "+wc.minX);
				writer.newLine();
				writer.write("Max-X: "+wc.maxX);
				writer.newLine();
				writer.write("Min-Z: "+wc.minZ);
				writer.newLine();
				writer.write("Max-Z: "+wc.maxZ);
				writer.newLine();
				writer.write("EndWorld");
				writer.newLine();
				writer.newLine();
			}
			writer.write("World: Default");
			writer.newLine();
			writer.write("Min-X: "+defaultBorder.minX);
			writer.newLine();
			writer.write("Max-X: "+defaultBorder.maxX);
			writer.newLine();
			writer.write("Min-Z: "+defaultBorder.minZ);
			writer.newLine();
			writer.write("Max-Z: "+defaultBorder.maxZ);
			writer.newLine();
			writer.write("EndWorld");
		} catch(IOException ioe) {}
	}
}
