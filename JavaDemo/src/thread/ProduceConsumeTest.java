package thread;
/**
 * 生产消费模式
 * @author PC-2020
 *
 */
public class ProduceConsumeTest {
	public static void main(String[] args) {
		SyncContainer sc = new SyncContainer();
		
		new Thread(new Producer(sc),"P").start();
		
		new Thread(new Consumer(sc),"C").start();
	}
}

//生产者
class Producer implements Runnable{
	SyncContainer sc;
	public Producer(SyncContainer sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		 for(int i=0;i<100;i++){
			 Ware w = new Ware(i);
			 System.out.println("生产"+w);
			 sc.produce(w);
		 }
	}
}
//消费者
//生产者
class Consumer implements Runnable{
	
	SyncContainer sc;
	public Consumer(SyncContainer sc) {
		this.sc = sc;
	}
	
	@Override
	public void run() {
		 for(int i=0;i<100;i++){
			 Ware w = sc.consume();
			 System.out.println("消费 "+w.toString());
		 }
	}
}
//缓冲区
class SyncContainer{
	Ware[] ware = new Ware[10];
	int count = 0 ;
	
//	生产
	public synchronized void produce(Ware w){
		if(count == ware.length){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ware[count] = w;
		count++;
		this.notifyAll();
	}
//	 消费
	public synchronized Ware consume(){
		if(count==0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count--;
		Ware w = ware[count];
		this.notifyAll();
		return w;
	}
}
//商品
class Ware{
	int id;

	public Ware(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Ware [id=" + id + "]";
	}
	
	
}
