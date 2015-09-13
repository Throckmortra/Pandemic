package ramhacks.pandemic.Models;

import com.reimaginebanking.api.java.models.Address;
import com.reimaginebanking.api.java.models.Geocode;

/**
 * Created by aaron on 9/12/15.
 */
public class MerchantWeight {
    private int weight;
    private Geocode geocode;
    private String id;
    private String name;

    public MerchantWeight(String id, String name, Geocode geocode, int weight) {
        this.weight = weight;
        this.geocode = geocode;
        this.id = id;
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Geocode getGeocode() {
        return geocode;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void incrementWeight(){
        if(weight == 1){
            weight++;
        }
        else{
            weight = weight + 1;
        }

    }

}