import java.util.concurrent.atomic.AtomicReference;


public class Score implements Comparable<Score> {

	private final int score;
	private final int userId;
	private final AtomicReference<Score> next;

	public Score(int score, int userId) {
		this.score = score;
		this.userId = userId;
		this.next = new AtomicReference<Score>();
	}

	public int getScore() {
		return score;
	}

	public int getUserId() {
		return userId;
	}
	
	public AtomicReference<Score> getNext() {
		return next;
	}

	public synchronized void setNextScore(Score nextScore) {
		this.next.set(nextScore);
	}
	
	public synchronized boolean removeThis(AtomicReference<Score> pointer) {
		return pointer.compareAndSet(this, this.next.get());
	}

	@Override
	public int compareTo(Score other) {
		return this.score - other.score;
	}

}
