import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton 패턴을 사용하여 글로벌하게 사용할 수 있는 map을 생성
 */
public class GlobalMap {
  static Map<String, Integer> map = new HashMap<>();

  public static GlobalMap getInstance(){
    return LazyHolder.INSTANCE;
  }

  /**
   * 멀티쓰레드 환경에서 인스턴스가 여러개 생성 되는 경우가 있음.
   * JVM은 static class를 초기화 할 때 멀티 쓰레드 환경에서도 원자성을 보장한다.
   * 때문에, 외부에서 getInstance()를 호출할 때 static한 LazyHolder class의 초기화를 진행하여
   * 멀티쓰레드 환경에서의 안정성을 확보할 수 있다.
   */
  private static class LazyHolder {
    private static final GlobalMap INSTANCE = new GlobalMap();
  }

  /**
   * 날짜기준으로 맵에서 밸류를 가져오며 (ex : seq_no) 다음 밸류를 입력
   */
  public int getSeqNo(){
    Date todayDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String today = sdf.format(todayDate);

    if (map.get(today) != null){
      int val = map.get(today) + 1;
      map.put(today, val);
      return val;
    } else {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(todayDate);
      calendar.add(Calendar.DATE, -2);
      Date yesterdayDate = calendar.getTime();
      String yesterday = sdf.format(yesterdayDate);
      if (map.get(yesterday) != null){
        map.remove(yesterday);
      }
      map.put(today, 1);
      return 1;
    }
  }

  /**
   * 서버가 재시작 될 때(이 경우 기존 map이 초기화되며 1부터 반환)을 대비해 값을 set 하는 메서드
   */
  public void setSeqNo(int i){
    Date todayDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String today = sdf.format(todayDate);
    map.put(today, i);
  }
}
