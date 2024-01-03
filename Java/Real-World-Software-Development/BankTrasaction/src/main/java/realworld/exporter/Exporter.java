package realworld.exporter;

import realworld.SummaryStatistics;

public interface Exporter {
    String export(SummaryStatistics summaryStatistics);
}
