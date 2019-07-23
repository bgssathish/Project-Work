import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.opencsv.CSVWriter;

public class jsonFlattener {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try {
			int i=1;
			List<String[]> x = new LinkedList<>();
			Set<String> a = new LinkedHashSet<>(); 
			Map<String, Object> flattenedJsonMap;
			String[] str = new String(Files.readAllBytes(Paths.get("D:\\My File_674335\\csvConversion\\24-06-2019\\intake_sess.json")))  //json
					.split("[.*}],[{.*]"); //"/\\* \\d+ \\*/"
			for (int j = 0; j < str.length; j++) {
				if(j!=str.length-1)
					str[j]+="}";
				if(j!=0)
					str[j]="{"+str[j];
				
			}
			for (i=0; i<str.length;i++) {
				Object obj = parser.parse(str[i]);
				JSONObject jsonObject = (JSONObject) obj;
				flattenedJsonMap = new LinkedHashMap<>(JsonFlattener.flattenAsMap(jsonObject.toString()));
				a.addAll(flattenedJsonMap.keySet());
				Map<String, Object> f = new LinkedHashMap<>();
				for (String w : a) {
					f.put(w, "");
				}
				List<String> strarr = new LinkedList<>();
				for (Map.Entry<String, Object> s : flattenedJsonMap.entrySet()) {
					f.put(s.getKey(), s.getValue());
				}
				for (Object o : f.values()) {
					strarr.add(o.toString());
				}
				x.add(strarr.toArray(new String[0]));
			}
			FileWriter outputfile = new FileWriter("D:\\My File_674335\\csvConversion\\24-06-2019\\intake_sess.csv");
			CSVWriter writer = new CSVWriter(outputfile);
			writer.writeNext(a.toArray(new String[0]));
			writer.writeAll(x);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
