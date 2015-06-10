package net.issoa.db;

public class ReciterObj {
	
	//private variables
		int _id;
		String _album_id;
		String _reciterid;
		String _surah_name_e;
		String _surah_name_a;
		String _audio_url;
		String _thumb_url;
		String _duration; 
		
		// Empty constructor
		public ReciterObj(){
			
		}
		// constructor
		public ReciterObj(int id, 
				String album_id, 
				String reciterid,
				String surah_name_e, 
				String surah_name_a,
				String audio_url, 
				String thumb_url,
				String duration
				)
		{
			this._id = id;
			this._album_id = album_id; 
			this._reciterid = reciterid; 
			this._surah_name_e = surah_name_e;
			this._surah_name_a = surah_name_a;
			this._audio_url = audio_url;
			this._thumb_url = thumb_url;
			this._duration = duration;
		}
		
		// constructor
		public ReciterObj(
				String album_id, 
				String reciterid,
				String surah_name_e, 
				String surah_name_a,
				String audio_url, 
				String thumb_url,
				String duration)
		{
			this._album_id = album_id; 
			this._reciterid = reciterid; 
			this._surah_name_e = surah_name_e;
			this._surah_name_a = surah_name_a;
			this._audio_url = audio_url;
			this._thumb_url = thumb_url;
			this._duration = duration;
		}
		// getting ID
		public int getID(){
			return this._id;
		}
		
		// setting id
		public void setID(int id){
			this._id = id;
		}
		
		// getting name
		public String getAlbumID(){
			return this._album_id;
		}
		
		// setting name
		public void setAlbumID(String albumID){
			this._album_id = albumID;
		}
		
		// getting name
				public String getReciterID(){
					return this._reciterid;
				}
				
				// setting name
				public void setReciterID(String reciterID){
					this._reciterid = reciterID;
				}
		
		// getting name
		public String getSurahNameE(){
			return this._surah_name_e;
		}
		
		// setting name
		public void setSurahNameE(String surah_name_e){
			this._surah_name_e = surah_name_e;
		}		
		
		// getting name
		public String getSurahNameA(){
			return this._surah_name_a;
		}
		
		// setting name
		public void setSurahNameA(String surah_name_a){
			this._surah_name_a = surah_name_a;
		}		
		
		
		// getting name
		public String getAudioURL(){
			return this._audio_url;
		}
		
		// setting name
		public void setAudioURL(String audio_url){
			this._audio_url = audio_url;
		}		
		
		// getting name
		public String getThumbURL(){
			return this._thumb_url;
		}
		
		// setting name
		public void setThumbURL(String thumb_url){
			this._thumb_url = thumb_url;
		}		
		
		// getting name
		public String getDuration(){
			return this._duration;
		}
		
		// setting name
		public void setDuration(String duration){
			this._duration = duration;
		}
		
		
		
		 

}
