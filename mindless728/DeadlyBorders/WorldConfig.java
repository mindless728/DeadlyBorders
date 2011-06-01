package mindless728.DeadlyBorders;

public class WorldConfig {
	protected int minX;
	protected int maxX;
	protected int minZ;
	protected int maxZ;
	protected String worldName;

	public WorldConfig() {
		minX = maxX = minZ = maxZ = 0;
	}

	public WorldConfig(String nm, int mx, int Mx, int mz, int Mz) {
		worldName = nm;
		minX = mx;
		maxX = Mx;
		minZ = mz;
		maxZ = Mz;
	}
}
