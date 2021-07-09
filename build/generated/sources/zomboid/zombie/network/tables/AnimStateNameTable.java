package zombie.network.tables;

public class AnimStateNameTable extends GenericNameTable {
   private static final AnimStateNameTable s_instance = new AnimStateNameTable();

   public static AnimStateNameTable getInstance() {
      return s_instance;
   }
}
