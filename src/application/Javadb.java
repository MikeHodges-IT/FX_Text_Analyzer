package application;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class Javadb {
		String databaseName = "wordoccurrencesdb";
		String url    = "jdbc:MySql://localhost:3306/"+ databaseName+"?useSSL=false";
    	String user     = "root";
    	String password = "password";
     	
	/**
	 * 
	 */
	public Javadb() {
	
	}
	/**
	 * @param databaseName
	 */
	public Javadb(String databaseName) {
		this.databaseName = databaseName;
	}
	/**
	 * @param databaseName
	 * @param url
	 */
	public Javadb(String databaseName, String url) {
		this.databaseName = databaseName;
		this.url = url;
	}
	/**
	 * @param databaseName
	 * @param url
	 * @param user
	 * @param password
	 */
	public Javadb(String databaseName, String url, String user, String password) {
		this.databaseName = databaseName;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	/**
	 * Execute an Sql Statement
	 * 
	 * @param sql
	 */
	public void sql(String sql) {
//		System.out.println("Running SQL");
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connection connection =  DriverManager.getConnection(url, user, password);
				PreparedStatement ps  = connection.prepareStatement(sql);
				int status = ps.executeUpdate();
				if(status != 0) {
//					System.out.println("SQL Success");
				
				}
				
				
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	/**
	 * Covert a string remove html from string and insert the data into a database.
	 * Then uses Sql Sql stored procedures to count the word occurrences and insert 
	 * the record set into a  LinkedHashMap. LinkedHashMap String, Long of value pair
	 * containing words and how many times each word occurred in the string. 
	 * 
	 * 
	 * @param  str String of words to count how many time each word occurred.
	 * @return wordFreqHashMap hashmap of value pair containing words and how many times each word occurred in the string
	 */
	public LinkedHashMap<String,Long> sqlCountToHashMap(String str) {
//		System.out.println("Running SQL count");
        String word ;
        long  count ;
		ResultSet rs = null;
		Statement st = null;
		
		
		LinkedHashMap<String, Long>wordFreqHashMap = new LinkedHashMap<String, Long>();
		Javadb j = new Javadb();
			j.sql("Call deleteAllWord();");
			Stream.of(
			str
			.replaceAll("&mdash;" , " ")
			.replaceAll("<[^>]*>"," ")
			.replaceAll("[\\s+\\W\\d]", " ")
			.trim()			
			.toLowerCase()
			.split("\\s+")).forEach(e ->{ j.sql("Call insertWord('"+ e +"')");});
			 

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connection connection =  DriverManager.getConnection(url, user, password);
				st  = connection.createStatement();
				rs  = st.executeQuery("Call wordCount;");
				System.out.println("Running getResultSet()");
	            while(rs.next()){
	                word = rs.getString("word");
	                count = rs.getLong("cnt");
	                
	                System.out.println(word + " " + count);
	                // set data in the hashmap
	                wordFreqHashMap.put(word,count);
	            }
			
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		System.out.println(wordFreqHashMap);
			return  wordFreqHashMap;
		 
	}
	
	
	
	
	
}
