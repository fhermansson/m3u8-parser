package io.lindstrom.m3u8.model;

import org.immutables.value.Value;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Media Segment interface
 */
@Value.Immutable
public interface MediaSegment {
    double duration();

    Optional<String> title();

    String uri();

    Optional<ByteRange> byteRange();

    Optional<OffsetDateTime> programDateTime();

    Optional<MapInfo> mapInfo();

    Optional<SegmentKey> segmentKey();

    @Value.Default
    default boolean discontinuity() {
        return false;
    }

    static Builder builder() {
        return new Builder();
    }

    class Builder extends MediaSegmentBuilder {
    }
}
