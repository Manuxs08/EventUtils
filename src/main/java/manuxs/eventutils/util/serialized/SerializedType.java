package manuxs.eventutils.util.serialized;

public class SerializedType<T> {
    private final String class_name;
    private final Class<T> tClass;

    protected SerializedType(Class<T> tClass){
        this.tClass = tClass;
        this.class_name = tClass.getSimpleName();
    }

    public String name(){
        return this.class_name;
    }

    public Class<T> typeClass(){
        return this.tClass;
    }
}
