/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.net;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import ch.epfl.tchu.SortedBag;

public interface Serde<T> {

    /**
     * @param t, the object to serialize
     * @return the string corresponding to the object to serialize
     */

    String serialize(T t);

    /**
     * @param s, the string to deserialize
     * @return the object corresponding to the string to deserialize
     */

    T deserialize(String s);

    /**
     * @param <T>         The method's parameter type
     * @param serialize   the function of serialization
     * @param deserialize the functionof deserialization
     * @return  the corresponding serde
     */

    static <T> Serde<T> of(Function<T, String> serialize,
                           Function<String, T> deserialize) {
        return new Serde<T>() {
            @Override
            public String serialize(T t) {
                return serialize.apply(t);
            }

            @Override
            public T deserialize(String s) {
                return deserialize.apply(s);
            }
        };
    }

    /**
     * @param <T>   The method's parameter type
     * @param list: the list of all the values corresponding to the values of an
     *              enum type
     * @return the corresponding serde (serialized or deserialized)
     */

    static <T> Serde<T> oneOf(List<T> list) {

        return new Serde<T>() {
            @Override
            public String serialize(T t) {
                Integer index = list.indexOf(t);
                return index.toString();
            }

            @Override
            public T deserialize(String s) {
                return list.get(Integer.parseInt(s));
            }
        };
    }

    /**
     *
     * @param serde, the givn serde
     * @param separation, the separator character
     * @param <T>, the object of the serde
     * @return a serde capable of (de) serializing lists of values (de) serialized by the given serde
     */

    static <T> Serde<List<T>> listOf(Serde<T> serde, String separation) {
        return new Serde<>() {
            @Override
            public String serialize(List<T> t) {
                String[] list = new String[t.size()];
                for (int i = 0; i < t.size(); ++i)
                    list[i] = serde.serialize(t.get(i));
                return String.join(separation, list);
            }

            @Override
            public List<T> deserialize(String s) {
                if (s.isEmpty()) return List.of();

                String[] list = s.split(Pattern.quote(separation), -1);
                List<T> list2 = new ArrayList<>();
                for (int i = 0; i < list.length; ++i)
                    list2.add(serde.deserialize(list[i]));

                return list2;
            }
        };
    }

    /**
     *
     * @param serde, the given serde
     * @param separation, the separator character
     * @param <T>, the object of the serde
     * @return a serde capable of (de) serializing sorted bag of values (de) serialized by the given serde
     */

    static <T extends Comparable<T>> Serde<SortedBag<T>> bagOf(
            Serde<T> serde, String separation) {
        return new Serde<>() {

            @Override
            public String serialize(SortedBag<T> t) {
                return Serde.listOf(serde, separation).serialize(t.toList());
            }

            @Override
            public SortedBag<T> deserialize(String s) {
                return SortedBag.of(Serde.listOf(serde, separation).deserialize(s));
            }
        };
    }
}
