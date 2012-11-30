package org.highj.data.collection;

import org.highj._;
import org.highj.data.tuple.T2;
import org.highj.function.F0;
import org.highj.function.F1;
import org.highj.function.Integers;
import org.highj.function.Strings;
import org.highj.typeclass.monad.Monad;
import org.highj.util.ReadOnlyIterator;
import org.junit.Test;

import java.util.Iterator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.highj.data.collection.Stream.*;

public class StreamTest {
    @Test
    public void testNarrow() throws Exception {
        _<Stream.µ, String> foo = of("foo");
        Stream<String> stream = narrow(foo);
        assertEquals("foo", stream.head());
    }

    @Test
    public void testHead() throws Exception {
        Stream<String> stream = of("foo");
        assertEquals("foo", stream.head());
        stream = Cons("foo", of("bar", "baz"));
        assertEquals("foo", stream.head());
        stream = Cons("foo", F0.constant(of("bar", "baz")));
        assertEquals("foo", stream.head());
        stream = unfold(Strings.append.flip().$("!"),"foo");
        assertEquals("foo", stream.head());
    }

    @Test
    public void testTail() throws Exception {
        Stream<String> stream = of("foo");
        assertEquals("foo", stream.tail().head());
        stream = Cons("foo", of("bar", "baz"));
        assertEquals("bar", stream.tail().head());
        stream = Cons("foo", F0.constant(of("bar", "baz")));
        assertEquals("bar", stream.tail().head());
        stream = unfold(Strings.prepend.$("!"),"foo");
        assertEquals("foo!", stream.tail().head());
    }

    @Test
    public void testStreamRepeat() throws Exception {
        Stream<String> stream = of("foo");
        for (int i = 1; i < 10; i++) {
            assertEquals("foo", stream.head());
            stream = stream.tail();
        }
    }

    @Test
    public void testStreamHeadFn() throws Exception {
        Stream<String> stream = unfold(Strings.prepend.$("!"),"foo");
        assertEquals("foo", stream.head());
        stream = stream.tail();
        assertEquals("foo!", stream.head());
        stream = stream.tail();
        assertEquals("foo!!", stream.head());
    }

    @Test
    public void testStreamHeadStream() throws Exception {
        Stream<String> stream = Cons("foo", of("bar"));
        assertEquals("foo", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
    }

    @Test
    public void testStreamHeadThunk() throws Exception {
        Stream<String> stream = Cons("foo", F0.constant(of("bar")));
        assertEquals("foo", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
    }

    @Test
    public void testStreamIterator() throws Exception {
        Iterator<Integer> myIterator = new ReadOnlyIterator<Integer>(){

            private int i = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return i++;
            }
        };

        Stream<Integer> stream = fromIterator(myIterator);
        assertEquals("Stream(0,1,2,3,4,5,6,7,8,9...)", stream.toString());
    }

    @Test
    public void testFilter() throws Exception {
        Stream<Integer> stream = range(1).filter(Integers.even);
        assertEquals(Integer.valueOf(2), stream.head());
        stream = stream.tail();
        assertEquals(Integer.valueOf(4), stream.head());
        stream = stream.tail();
        assertEquals(Integer.valueOf(6), stream.head());

    }

    @Test
    public void testToString() throws Exception {
        Stream<Integer> stream = range(1).filter(Integers.even);
        assertEquals("Stream(2,4,6,8,10,12,14,16,18,20...)", stream.toString());
    }

    @Test
    public void testTake() throws Exception {
        Stream<Integer> stream = range(1).filter(Integers.odd);
        assertEquals("List(1,3,5,7)", stream.take(4).toString());
        assertEquals("List()", stream.take(0).toString());
        assertEquals("List()", stream.take(-4).toString());
    }

    @Test
    public void testTakeWhile() throws Exception {
        Stream<Integer> stream = range(10,-3);
        assertEquals("List(10,7,4,1)", stream.takeWhile(Integers.positive).toString());
        assertEquals("List()", stream.takeWhile(Integers.negative).toString());
    }

    @Test
    public void testDrop() throws Exception {
        Stream<Integer> stream = range(1);
        assertEquals(Integer.valueOf(5), stream.drop(4).head());
        assertEquals(Integer.valueOf(1), stream.drop(0).head());
        assertEquals(Integer.valueOf(1), stream.drop(-4).head());
    }

    @Test
    public void testDropWhile() throws Exception {
        Stream<Integer> stream = range(10,-3);
        assertEquals(Integer.valueOf(-2), stream.dropWhile(Integers.positive).head());
        assertEquals(Integer.valueOf(10), stream.dropWhile(Integers.negative).head());
    }

    @Test
    public void testRangeFrom() throws Exception {
        Stream<Integer> stream = range(10);
        assertEquals("List(10,11,12,13)", stream.take(4).toString());
    }

    @Test
    public void testRangeFromTo() throws Exception {
        Stream<Integer> stream = range(10,3);
        assertEquals("List(10,13,16,19)", stream.take(4).toString());
        stream = range(10, 0);
        assertEquals("List(10,10,10,10)", stream.take(4).toString());
        stream = range(10,-3);
        assertEquals("List(10,7,4,1)", stream.take(4).toString());
    }

    @Test
    public void testCycle() throws Exception {
        Stream<String> stream = of("foo", "bar", "baz");
        assertEquals("List(foo,bar,baz,foo,bar,baz,foo)", stream.take(7).toString());
    }

    @Test
    public void testMap() throws Exception {
        Stream<Integer> stream = of("one", "two", "three").map(Strings.length);
        assertEquals("List(3,3,5,3,3,5,3)", stream.take(7).toString());

    }

    @Test
    public void testZip() throws Exception {
        Stream<T2<Integer,String>> stream = zip(range(1), of("foo", "bar", "baz"));
        assertEquals("List((1,foo),(2,bar),(3,baz),(4,foo))", stream.take(4).toString());
    }

    @Test
    public void testZipWith() throws Exception {
        Stream<String> stream = zipWith(Strings.repeat, of("foo", "bar", "baz"), range(2));
        assertEquals("List(foofoo,barbarbar,bazbazbazbaz,foofoofoofoofoo)", stream.take(4).toString());
    }

    @Test
    public void testUnzip() throws Exception {
        Stream<T2<Integer,String>> stream = zip(range(1), of("foo", "bar", "baz"));
        T2<Stream<Integer>, Stream<String>> t2 = unzip(stream);
        assertEquals("List(1,2,3,4)", t2._1().take(4).toString());
        assertEquals("List(foo,bar,baz,foo)", t2._2().take(4).toString());
    }

    @Test
    public void testIterator() throws Exception {
        int n = 3;
        for(int i : range(3)) {
            assertEquals(n, i);
            n++;
            if (n > 10) {
                return;
            }
        }
    }

    @Test
    public void testMonad() throws Exception {
        Monad<Stream.µ> monad = Stream.monad;

        Stream<String> foobars = Cons("foo", of("bars"));
        Stream<Integer> foobarsLength = narrow(monad.map(Strings.length, foobars));
        assertEquals("Stream(3,4,4,4,4,4,4,4,4,4...)", foobarsLength.toString());

        Stream<String> foos = narrow(monad.pure("foo"));
        assertEquals("Stream(foo,foo,foo,foo,foo,foo,foo,foo,foo,foo...)", foos.toString());

        Stream<Integer> absSqr = narrow(monad.ap(of(Integers.negate, Integers.sqr), range(1)));
        assertEquals("Stream(-1,4,-3,16,-5,36,-7,64,-9,100...)", absSqr.toString());

        Stream<Integer> streamOfStream = narrow(monad.bind(range(1),
                new F1<Integer, _<µ, Integer>>() {
                    @Override
                    public _<Stream.µ, Integer> $(Integer integer) {
                        return range(1, integer);
                    }
                }));
        assertEquals("Stream(1,3,7,13,21,31,43,57,73,91...)", streamOfStream.toString());
    }
}
