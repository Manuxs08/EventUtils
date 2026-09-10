package manuxs.eventutils.util.serialized;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class SerializedData<T> {
    private static final String prefix = "[SerializedInstruction]";
    private final String category;
    private final SerializedType<T> type;
    private final T value;

    private SerializedData(String category, SerializedType<T> type, T value){
        this.category = category;
        this.type = type;
        this.value = value;
    }

    private SerializedData(String serialized_data, SerializedType<T> type) throws NumberFormatException{
        String category = "";
        T value = null;
        String[] split_data = splitData(serialized_data);
        for (String data : split_data){
            if(data.startsWith("category:")){
                category = data.replace("category:","");
            }else if(data.startsWith("value:")){
                String value_text = data.replace("value:","");
                if(type == SerializedTypes.INT){
                    value = (T) Integer.valueOf(value_text);
                } else if (type == SerializedTypes.LONG) {
                    value = (T) Long.valueOf(value_text);
                } else if (type == SerializedTypes.DOUBLE) {
                    value = (T) Double.valueOf(value_text);
                } else if (type == SerializedTypes.BOOL) {
                    value = (T) Boolean.valueOf(value_text);
                } else if (type == SerializedTypes.STRING) {
                    value = (T) value_text;
                }
            }
        }

        if(value == null) throw new NumberFormatException("Value not found for this serialized data type ("+type.typeClass().getSimpleName()+")");

        this.category = category;
        this.type = type;
        this.value = value;
    }

    public T value(){
        return this.value;
    }

    public static String[] splitData(String message){
        String removed_prefix = message.replace(prefix,"");
        return removed_prefix.split(";");
    }

    public static String getCategory(String message){
        if(!hasPrefix(message)) return "";

        String[] split_data = splitData(message);
        for (String data : split_data){
            if(data.startsWith("category:")){
                return data.replace("category:","");
            }
        }

        return "";
    }

    public static boolean hasPrefix(String message){
        return message.startsWith(prefix);
    }

    public static <T> SerializedData<T> create(String category, SerializedType<T> type, T value){
        return new SerializedData<>(category,type,value);
    }

    @Nullable
    public static <T> SerializedData<T> deserialize(String serialized_data, SerializedType<T> type){
        try {
            return new SerializedData<>(serialized_data,type);
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String serialize(){
        return prefix+"category:"+category+";variable_type:"+this.type.name()+";value:"+this.value.toString();
    }
}
