
/**
 * @author Group9 - Kitten & Michael & Tom
 * This is the structure of the class Item
 */

public class Item {
	
	String keyword;
	String url;
	Boolean sponsor;
	int count = 0;
	String title;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getSponsor() {
		return sponsor;
	}
	public void setSponsor(Boolean sponsor) {
		this.sponsor = sponsor;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
