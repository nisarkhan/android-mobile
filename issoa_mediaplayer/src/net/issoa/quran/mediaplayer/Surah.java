package net.issoa.quran.mediaplayer;

public class Surah {

	private String surahId;
	private String surahName;
    private String surahMeaning;
    private String surahUrl;
    
    public String getSurahId() {
        return surahId;
    }
    public void setSurahId(String id) {
        this.surahId = id;
    }
    
    
    public String getSurahName() {
        return surahName;
    }
    public void setSurahName(String name) {
        this.surahName = name;
    }
    
    public String getSurahMeaning() {
        return surahMeaning;
    }
    public void setSurahMeaning(String meaning) {
        this.surahMeaning = meaning;
    }
    
    public String getSurahUrl() {
        return surahUrl;
    }
    public void setSurahUrl(String url) {
        this.surahUrl = url;
    }
    
    public Surah(String id, String name, String meaning, String url)
    {
    	setSurahId(id);
    	setSurahName(name);
    	setSurahMeaning(meaning);    	
    	setSurahUrl(url);
    	
    }
}
