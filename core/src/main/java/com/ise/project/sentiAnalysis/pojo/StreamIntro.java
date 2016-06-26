//package com.ise.project.sentiAnalysis.pojo;
//
//
//import com.google.common.collect.ImmutableList;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//import static java.util.Comparator.comparing;
//import static java.util.stream.Collectors.toList;
//
//public class StreamIntro {
//    private static final Logger logger = LoggerFactory.getLogger(StreamIntro.class);
//
//    @Test
//    public void test7to8() throws Exception {
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(Transaction.newBuilder().id(1).value(10).type(Transaction.GROCERY).build());
//        transactions.add(Transaction.newBuilder().id(2).value(20).type(Transaction.GROCERY).build());
//        transactions.add(Transaction.newBuilder().id(3).value(30).type(Transaction.OTHER).build());
//
//        // imperative code to collect id's of sorted Grocery transactions
//        List<Transaction> groceryTransactions = new ArrayList<>();
//        for(Transaction t: transactions){
//            if(Objects.equals(t.getType(), Transaction.GROCERY)){
//                groceryTransactions.add(t);
//            }
//        }
//        Collections.sort(groceryTransactions, new Comparator<Transaction>() {
//            public int compare(Transaction t1, Transaction t2) {
//                return t2.compareTo(t1);
//            }
//        });
//        List<Integer> transactionIds = new ArrayList<>();
//        for(Transaction t: groceryTransactions){
//            transactionIds.add(t.getId());
//        }
//
//        // Functional code using stream api to collect id's of sorted Grocery transactions
//        List<Integer> transactionsIds =
//                transactions.stream()
//                        .filter(t -> Objects.equals(t.getType(), Transaction.GROCERY))
//                        .sorted(comparing(Transaction::getValue).reversed())
//                        .map(Transaction::getId)
//                        .collect(toList());
//
//        assertThat(transactionIds).containsExactlyElementsOf(transactionsIds);
//    }
//
//    @Test
//    public void testLambda() throws Exception {
//        //  This is a lambda expr: (input args) -> { operation on the inputs}
//        //  Ex: (Integer i) -> i * 2;
//
//        //  In Java lambda expr maps to a single method interface, i.e Functional Interface
//        final Function<Integer, Integer> lambda = (Integer i) -> i * 2; // i's type is inferred by compiler
//
//        final BiFunction<Integer, Integer, Integer> lambda2 = (i, j) -> i + j;
//
//        final Predicate<Integer> evenFunctionalInterface = i -> (i % 2 == 0);
//        final Predicate<Integer> evenSingleAbstractMethod = new Predicate<Integer>() {
//            @Override
//            public boolean apply(Integer i) {
//                return i % 2 == 0;
//            }
//        };
//
//        assertTrue(evenFunctionalInterface.apply(8) == evenSingleAbstractMethod.apply(10));
//
//
//        // To use preexisting methods as lambda's we use Method reference
//        // Ex: List::add refers to add in List interface,
//        //     System.out::println refers to println of output stream.
//
//        Collections.sort(intListMutable(), (i, j) -> i.compareTo(j)); // sort using comparator
//        Collections.sort(intListMutable(), Integer::compareTo); // or use method ref
//
//        // you can refer to constructors using '::new'
//        // ArrayList::new; HashSet::new etc
//    }
//
//    @Test
//    public void testOptional() throws Exception {
//        final Optional<String> fname = Optional.of("fname"); // create an optional.
//        Optional.empty(); // empty
//
//        String name = null;
//        final Optional<String> nullable = Optional.ofNullable(name); // from a var that can be null
//
//        final boolean fnamePresent = fname.isPresent();
//
//        final Optional<String> filter = fname.filter("fname"::equals);
//
//        final Optional<Integer> integer = fname.map(String::length); // if exists map it to it's length
//
//        final String orElse = nullable.orElse("fname"); // if not present return the provided val
//
//        nullable.ifPresent(logger::info); // if present pass to lambda
//
//        final Optional<String> optional = nullable.flatMap(Optional::of); // flatten nested optional
//    }
//
//    /**
//     * Stream API
//     * Please read the following before proceeding:
//     * https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
//     */
//
//
//    @Test
//    public void testCreateStream() throws Exception {
//        final Stream<String> alphabetStream = Stream.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m");
//        Stream.of("fname"); // single element stream
//        Stream.empty(); // empty stream
//
//        Stream.generate(Math::random); // Stream of rand doubles, infinite
//
//        // Stream form collections
//        final Stream<Integer> stream = intList().stream();
//        Arrays.stream(new String[]{"fname", "lname"});
//
//        // Stream lines from files
//        final Stream<String> lines = Files.lines(Paths.get(""));
//
//        // Parallel streams: run stream op's on multiple cores, uses ForkJoin common pool internally
//        // please beware that parallel stream are not suitable for all cases and should be used carefully
//        final Stream<Integer> integerStream = intList().parallelStream();
//        final Stream<String> parallel = alphabetStream.parallel(); // convert an existing stream to parallel
//    }
//
//    @Test
//    public void testStreamIntermediateOps() throws Exception {
//        final Stream<Integer> distinct = intList()
//                .stream()
//                .distinct(); // distinct is an intermediate op
//        // lazy, will not be exec unit we specify a terminal op
//        // all intermediate ops will be evaluated lazily
//
//        intList()
//                .stream()
//                .limit(2); // limits the stream to emit only 2 ints
//
//        intList()
//                .stream()
//                .skip(2); // skip's the stream's first 2 ints
//
//        intList()
//                .stream()
//                .filter(integer -> integer % 2 == 0); // filter all even ints
//
//        intList()
//                .stream()
//                .map(integer -> integer * 2); // double each int of stream
//
//        Stream<List<Integer>> integerListStream = Stream.of(
//                Arrays.asList(1, 2),
//                Arrays.asList(3, 4),
//                Arrays.asList(5, 6)
//        );
//
//        Stream<Integer> integerStream = integerListStream.flatMap(Collection::stream); // flatten nested streams
//
//        final Stream<Integer> sorted = intList().stream().sorted(); // emits elements in sorted order, stateful op!!
//
//        // IntSream is specialized for primitive int vals
//        // Similar specialization exist for most of the primitive types
//        final IntStream intStream = intList().stream().mapToInt(i -> i);
//    }
//
//    @Test
//    public void testStreamTerminalOps() throws Exception {
//
//        final Stream<String> alphabetStream = Stream.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m");
//
//        alphabetStream
//                .forEach(logger::info); // forEach is a terminal op, and stream is evaluated immediately
//
//        final List<Integer> integers = Stream.of(1, 2, 3, 4)
//                .collect(toList()); // collect is also a terminal op
//
//        // More terminal op's: count, max, min, reduce etc
//        final long count = Stream.of(1, 2, 3, 4).count();
//        final Optional<Integer> max = Stream.of(1, 2, 3, 4).max(Integer::compareTo);
//        final Optional<Integer> min = Stream.of(1, 2, 3, 4).min(Integer::compareTo);
//        final Optional<Integer> sum = Stream.of(1, 2, 3, 4).reduce((i, j) -> i + j);
//
//        final boolean anyMatch = Stream.of(1, 2, 3, 4)
//                .anyMatch(integer -> integer % 2 == 0);
//
//        final boolean allMatch = Stream.of(1, 2, 3, 4)
//                .allMatch(integer -> integer % 2 == 0);
//    }
//
//    @Test
//    public void testStreamCollection() throws Exception {
//        final Set<Integer> set = intList()
//                .stream()
//                .collect(Collectors.toSet()); // collect a stream to Set
//
//        final Map<Boolean, List<Integer>> collect = intList()
//                .stream()
//                .collect(Collectors.partitioningBy(i -> i % 2 == 0)); // partitioning collect to map with even/odd entries
//
//        // collect to a specific collection type
//        final LinkedHashSet<Integer> integers = intList()
//                .stream()
//                .collect(Collectors.toCollection(LinkedHashSet::new));
//
//        // collect to Guava's collections using MoreCollectors from guava-stream dependency
//        final ImmutableList<Integer> immutableList = intList()
//                .stream()
//                .collect(MoreCollectors.toImmutableList());
//
//    }
//
//    private List<Integer> intList() {
//        return IntStream.range(0, 100)
//                .boxed()
//                .collect(toImmutableList());
//    }
//
//    private List<Integer> intListMutable() {
//        return IntStream.range(0, 100)
//                .boxed()
//                .collect(toList());
//    }
//
//    public static class Transaction implements Comparable<Transaction> {
//
//        public static final String GROCERY = "GROCERY";
//        public static final String OTHER = "OTHER";
//        private int id;
//        private int value;
//        private String type;
//
//        private Transaction(Builder builder) {
//            setId(builder.id);
//            setValue(builder.value);
//            setType(builder.type);
//        }
//
//        public static Builder newBuilder() {
//            return new Builder();
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        @Override
//        public int compareTo(Transaction o) {
//            return Integer.compare(this.value, o.value);
//        }
//
//
//        public static final class Builder {
//            private int id;
//            private int value;
//            private String type;
//
//            private Builder() {
//            }
//
//            public Builder id(int id) {
//                this.id = id;
//                return this;
//            }
//
//            public Builder value(int value) {
//                this.value = value;
//                return this;
//            }
//
//            public Builder type(String type) {
//                this.type = type;
//                return this;
//            }
//
//            public Transaction build() {
//                return new Transaction(this);
//            }
//        }
//    }
//}