package Utils;

import java.util.*;

/**
 * This class handles the input commands entered by the user.
 */
public class Command {
	
	/**
	 * d_command stores the command player types in.
	 */
    public String d_command;

    /**
     * Sets the values of the data member.
     * 
     * @param p_command input command given by the player
     */
    public Command(String p_command){
        this.d_command = p_command.trim().replaceAll(" +", " ");
    }

    /**
     * Getter method for the root command.
     * 
     * @return string the rootcommand from the series of commands entered by the player
     */
    public String getRootCommand(){
        return d_command.split(" ")[0];
    }

    /**
     * Processes through the list of operations received from the player.
     * 
     * @return list the list of operations are returned
     */
    public List<Map<String , String>> getOperationsAndArguments(){
        String l_rootCommand = getRootCommand();
        String l_operationsString =  d_command.replace(l_rootCommand, "").trim();
        
        if(null == l_operationsString || l_operationsString.isEmpty()) {
        	return new ArrayList<Map<String , String>>();
        }
        boolean l_isFlagLessCommand = !l_operationsString.contains("-") && !l_operationsString.contains(" ");

        // handle commands to load files, ex: loadmap filename
        if(l_isFlagLessCommand){
            l_operationsString = "-filename "+l_operationsString;
        }

        List<Map<String , String>> l_operations_list  = new ArrayList<Map<String,String>>();
        String[] l_operations = l_operationsString.split("-");

        Arrays.stream(l_operations).forEach((operation) -> {
            if(operation.length() > 1) {
                l_operations_list.add(getOperationAndArgumentsMap(operation));
            }
        });

        return l_operations_list;
    }
    
    /**
     * Handles all the operations and arguments given by the player.
     * 
     * @param p_operation input operation given by the player
     * @return map the operation map is returned
     */
    private Map<String, String> getOperationAndArgumentsMap(String p_operation){
       Map<String, String> l_operationMap = new HashMap<String, String>();

        String[] l_split_operation = p_operation.split(" ");
        String l_arguments = "";

        l_operationMap.put("operation", l_split_operation[0]);

       if(l_split_operation.length > 1){
           String[] l_arguments_values = Arrays.copyOfRange(l_split_operation, 1, l_split_operation.length);
           l_arguments = String.join(" ",l_arguments_values);
       }

        l_operationMap.put("arguments", l_arguments);

        return l_operationMap;
    }
    
    /**
     * This method checks if the map chosen by the user consists of the specified keys or not.
     * 
     * @param p_key keys given by the player
     * @param p_inputMap the input map selected by the player
     * @return boolean true if the required keys are present and false if not
     */
    public boolean checkRequiredKeysPresent(String p_key, Map<String, String> p_inputMap) {
    	if(p_inputMap.containsKey(p_key) && null != p_inputMap.get(p_key)
				&& !p_inputMap.get(p_key).isEmpty())
    		return true;
    	return false;
    }

    /**
     * Getter for the command.
     *
     * @return command in string
     */
    public String getD_command() {
        return d_command;
    }
}
