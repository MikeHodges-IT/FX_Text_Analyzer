package application;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
/**
* <h1>TextAnalyzer</h1>
* 
* Has one method that takes a string and returns a linkHashMap of word occurrence.
* 
* <p>
* <b>Note:</b> The TextAnalyzer class contains the following methods:
*  public static LinkedHashMap String, Long htmlStringToFreqMap(String str)
* @author  Mike Hodges
* @version 1.0
* @since   2020-11-15
*/
public class TextAnalyzer {  

	/**
	 * Covert a string to a LinkedHashMap String, Long of value pair containing words and how many times each word occurred in the string. 
	 * 
	 * 
	 * @param  str String of words to count how many time each word occurred.
	 * @return wordFreqHashMap hashmap of value pair containing words and how many times each word occurred in the string
	 */
	public static LinkedHashMap<String, Long> htmlStringToFreqMap(String str){
		Map<String, Long> wordFreqMap =  
				
				Stream.of(
				str
				.replaceAll("&mdash;" , " ")
				.replaceAll("<[^>]*>"," ")
				.replaceAll("[\\s+\\W\\d]", " ")
				.trim()			
				.toLowerCase()
				.split("\\s+"))
				.collect(groupingBy(e ->e,counting())
				);
		 
		LinkedHashMap<String, Long>wordFreqHashMap = 
				
				wordFreqMap.entrySet()
						   .parallelStream()
		                   .sorted(Collections.reverseOrder(Entry.comparingByValue()))
		                   .limit(20)
		                   .collect(toMap(Entry::getKey, Entry::getValue, (e1,e2) -> e2,LinkedHashMap::new));	
		
//		System.out.println(wordFreqHashMap);
		return  wordFreqHashMap;
		
	}

	}
	
	
