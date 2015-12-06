import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YDNPC on 29/11/2015.
 */
public class Template {

    private Map<String, String> variableValue;
    private String templateText;

    public Template(String templateText) {
        this.variableValue = new HashMap<String, String>();
        this.templateText = templateText;
    }

    public void set(String name, String value) {
        this.variableValue.put(name, value);
    }

    public String evaluate() {
        String result = replaceVariables();
        checkForMissingValues(result);
        return result;
    }

    private String replaceVariables() {
        String result = templateText;
        for(Map.Entry<String, String> entry: variableValue.entrySet()){
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            result = result.replaceAll(regex, entry.getValue());
        }
        return result;
    }

    private void checkForMissingValues(String result) {
        Matcher m = Pattern.compile(".*\\$\\{.+\\}.*").matcher(result);
        if(m.find()){
            throw new MissingValueException("No value for " + m.group());
        }
    }
}
