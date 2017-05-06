package com.cumt.cs.trajectory.model.common;

import java.util.Comparator;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class QueryResult implements Comparator<QueryResult> {
    private int[] id = {0, 0};
    private double similarity = 0.0;

    public QueryResult(int id0, int id1, double sim) {
        id[0] = id0;
        id[1] = id1;
        similarity = sim;
    }

    /*boolean operator< (const QueryResult a,const QueryResult b)
    {
        if (fabs(a.similarity - b.similarity) > 1e-6)
            return a.similarity < b.similarity;
        return a.id[0] != b.id[0] ? a.id[0] < b.id[0] : a.id[1] < b.id[1];
    }*/

    @Override
    public int compare(QueryResult a, QueryResult b) {
        if (Math.abs(a.similarity - b.similarity) > 1e-6)
            return a.similarity < b.similarity ? 1 : -1;
        return (a.id[0] != b.id[0] ? a.id[0] < b.id[0] : a.id[1] < b.id[1]) ? 1 : -1;
    }
}
