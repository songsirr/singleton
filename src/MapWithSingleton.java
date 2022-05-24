public class MapWithSingleton {

  public static void main(String[] args){
    GlobalMap map = GlobalMap.getInstance();
    int i = map.getSeqNo();
  }
}
