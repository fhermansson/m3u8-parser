package io.lindstrom.m3u8.parser;

import io.lindstrom.m3u8.model.DateRange;

import java.time.OffsetDateTime;

import static io.lindstrom.m3u8.parser.Tags.YES;

enum DateRangeAttributes implements Attribute<DateRange, DateRange.Builder> {
    ID {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.id(value);
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            textBuilder.addQuoted(name(), value.id());
        }
    },

    CLASS {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.classAttribute(value);
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.classAttribute().ifPresent(v -> textBuilder.addQuoted(name(), v));
        }
    },

    START_DATE {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.startDate(OffsetDateTime.parse(value, ParserUtils.FORMATTER));
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            textBuilder.addQuoted(key(), value.startDate().toString());
        }
    },

    END_DATE {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.endDate(OffsetDateTime.parse(value, ParserUtils.FORMATTER));
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.endDate().ifPresent(v -> textBuilder.addQuoted(key(), v.toString()));
        }
    },

    DURATION {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.duration(Double.parseDouble(value));
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.duration().ifPresent(v -> textBuilder.add(name(), Double.toString(v)));
        }
    },

    PLANNED_DURATION {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.plannedDuration(Double.parseDouble(value));
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.plannedDuration().ifPresent(v -> textBuilder.add(key(), Double.toString(v)));
        }
    },

    SCTE35_CMD {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.scte35Cmd(value);
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.scte35Cmd().ifPresent(v -> textBuilder.add(key(), v));
        }
    },

    SCTE35_OUT {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.scte35Out(value);
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.scte35Out().ifPresent(v -> textBuilder.add(key(), v));
        }
    },

    SCTE35_IN {
        @Override
        public void read(DateRange.Builder builder, String value) {
            builder.scte35In(value);
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            value.scte35In().ifPresent(v -> textBuilder.add(key(), v));
        }
    },

    END_ON_NEXT {
        @Override
        public void read(DateRange.Builder builder, String value) throws PlaylistParserException {
            builder.endOnNext(ParserUtils.yesOrNo(value));
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            if (value.endOnNext()) {
                textBuilder.add(key(), YES);
            }
        }
    },

    CLIENT_ATTRIBUTE {
        @Override
        public void read(DateRange.Builder builder, String value) {
            throw new IllegalStateException();
        }

        @Override
        public void read(DateRange.Builder builder, String key, String value) {
            builder.putClientAttributes(key, value);
        }

        @Override
        public void write(DateRange value, TextBuilder textBuilder) {
            // TODO: support client attribute types (quoted-string, hexadecimal-sequence & decimal-floating-point)
            value.clientAttributes().forEach(textBuilder::addQuoted);
        }
    };

    static DateRange parse(String attributes) throws PlaylistParserException {
        DateRange.Builder builder = DateRange.builder();
        AbstractPlaylistParser.readAttributes(DateRangeAttributes.class, attributes, builder);
        return builder.build();
    }
}
