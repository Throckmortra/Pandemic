package ramhacks.pandemic.Models;

/**
 * Created by aaron on 9/12/15.
 */
public class Merchant
{
    private geocode geocode;
    private String _id;
    private address address;
    private String name;

    public geocode getGeocode ()
    {
        return geocode;
    }

    public String get_id ()
    {
        return _id;
    }


    public address getAddress ()
    {
        return address;
    }


    public String getName ()
    {
        return name;
    }

    public class address{
        private String street_name;
        private String zip;
        private String street_number;
        private String state;
        private String city;

        public String getStreet_name() {
            return street_name;
        }

        public String getZip() {
            return zip;
        }

        public String getStreet_number() {
            return street_number;
        }

        public String getState() {
            return state;
        }

        public String getCity() {
            return city;
        }
    }

    public class geocode{
        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }


}