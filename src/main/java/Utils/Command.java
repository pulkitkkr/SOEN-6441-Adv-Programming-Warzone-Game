package Utils;

import java.util.*;

public class Command {
    public String d_command;

    public Command(String p_command){
        this.d_command = p_command.trim().replaceAll(" +", " ");
    }

    public String getRootCommand(){
        return d_command.split(" ")[0];
    }

    public List<Map<String , String>> getOperationsAndArguments(){
        String l_rootCommand = getRootCommand();
        String l_operationsString =  d_command.replace(l_rootCommand, "").trim();
        String[] l_operations = l_operationsString.split("-");
        List<Map<String , String>> l_operations_list  = new ArrayList<Map<String,String>>();

        Arrays.stream(l_operations).forEach((operation) -> {
            if(operation.length() > 1) {
                l_operations_list.add(getOperationAndArgumentsMap(operation));
            }
        });

        return l_operations_list;
    }

    private Map<String, String>  getOperationAndArgumentsMap(String p_operation){
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
}
