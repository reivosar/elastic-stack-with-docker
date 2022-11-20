package reivosar.backendapp.infrastructure.elasticsearch.shared;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;

public class ElasticsearchParameter {

    private final String index;
    private final List<Pair> equals;
    private final List<Pair> likes;
    private final List<Pair> ranges;
    private final List<Pair> sorts;

    private ElasticsearchParameter(final Builder builder) {
        this.index = builder.index;
        this.equals = builder.equals;
        this.likes = builder.likes;
        this.ranges = builder.ranges;
        this.sorts = builder.sorts;
    }

    public static class Builder {
        private final String index;
        private final List<Pair> equals;
        private final List<Pair> likes;
        private final List<Pair> ranges;
        private final List<Pair> sorts;

        public Builder(final String index) {
            this.index = index;
            this.equals = new LinkedList<>();
            this.likes = new LinkedList<>();
            this.ranges = new LinkedList<>();
            this.sorts = new LinkedList<>();
        }

        public Builder equal(final Object key, final Object value) {
            this.equals.add(Pair.of(key, value));
            return this;
        }

        public Builder like(final Object key, final Object value) {
            this.likes.add(Pair.of(key, value));
            return this;
        }

        public Builder range(final Object key, final Object min, final Object max) {
            this.ranges.add(Pair.of(key, min, max));
            return this;
        }

        public Builder sort(final Object key, final Object value) {
            this.sorts.add(Pair.of(key, value));
            return this;
        }

        public ElasticsearchParameter build() {
            return new ElasticsearchParameter(this);
        }
    }

    public Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> query() {
        return query -> {
            query.index(ElasticsearchParameter.this.index);
            this.setEqualQuery(query);
            this.setLikeQuery(query);
            this.setRangeQuery(query);
            return query;
        };
    }

    private void setEqualQuery(final co.elastic.clients.elasticsearch.core.SearchRequest.Builder query) {
        if (!this.equals.isEmpty()) {
            query.query(equalQuery -> {
                ElasticsearchParameter.this.equals.stream().filter(Pair::hasValues).forEach(
                        equal -> equalQuery
                                .match(t -> t.field(equal.key().toString()).query(equal.firstValue().toString())));
                return equalQuery;
            });
        }
    }

    private void setLikeQuery(final co.elastic.clients.elasticsearch.core.SearchRequest.Builder query) {
        if (!this.likes.isEmpty()) {
            query.query(likeQuery -> {
                ElasticsearchParameter.this.likes.stream().filter(Pair::hasValues).forEach(
                        like -> likeQuery
                                .term(t -> t.field(like.key().toString()).value(like.firstValue().toString())));
                return likeQuery;
            });
        }
    }

    private void setRangeQuery(final co.elastic.clients.elasticsearch.core.SearchRequest.Builder query) {
        if (!this.ranges.isEmpty()) {
            query.query(rangeQuery -> {
                ElasticsearchParameter.this.ranges.stream().filter(like -> like.valueSize() == 2).forEach(
                        range -> rangeQuery.range(t -> t.field(range.key().toString()).from(range.get(0).toString())
                                .to(range.get(1).toString())));
                return rangeQuery;
            });
        }
    }
}
