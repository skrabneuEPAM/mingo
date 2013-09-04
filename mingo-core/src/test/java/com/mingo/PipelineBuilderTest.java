package com.mingo;

import com.google.common.collect.Maps;
import com.mingo.query.aggregation.PipelineBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Unit test for {@link PipelineBuilder}.
 */
public class PipelineBuilderTest {
    private PipelineBuilder pipelineBuilder = new PipelineBuilder();

    @Test
    public void testBuild() {
        Map<String, String> queries = Maps.newHashMap();
        queries.put("{$match : { \"moderationStatus\": { $in: \"['STATUS_NOT_MODERATED']\"}, \"tags\": { $in: \"\"}}}",
            "[ { \"$match\" : { \"moderationStatus\" : { \"$in\" : \"['STATUS_NOT_MODERATED']\"}}}]");
        queries.put("{$match : { \"moderationStatus\": { $in: \"\"}, \"tags\": { $in: \"['test']\"}}}",
            "[ { \"$match\" : { \"tags\" : { \"$in\" : \"['test']\"}}}]");
        queries.put("{$match : { \"moderationStatus\": { $in: \"\"}, \"tags\": { $in: \"\"}}}",
            "[ { \"$match\" : { }}]");
        for (Map.Entry<String, String> entry : queries.entrySet()) {
            Assert.assertEquals(pipelineBuilder.serialize(pipelineBuilder.buildAggregation(entry.getKey())), entry.getValue());
        }
    }

    @Test
    public void testBuildConcurrent() throws ExecutionException, InterruptedException {
        Map<String, String> queries = Maps.newHashMap();
        queries.put("{$match : { \"moderationStatus\": { $in: \"['STATUS_NOT_MODERATED']\"}, \"tags\": { $in: \"\"}}}",
            "[ { \"$match\" : { \"moderationStatus\" : { \"$in\" : \"['STATUS_NOT_MODERATED']\"}}}]");
        queries.put("{$match : { \"moderationStatus\": { $in: \"\"}, \"tags\": { $in: \"['test']\"}}}",
            "[ { \"$match\" : { \"tags\" : { \"$in\" : \"['test']\"}}}]");
        queries.put("{$match : { \"moderationStatus\": { $in: \"\"}, \"tags\": { $in: \"\"}}}",
            "[ { \"$match\" : { }}]");

        int countOfThreads = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(countOfThreads);

        List<Future<Integer>> tasks = new ArrayList<>(countOfThreads);

        Integer totalCheck = 0;
        for (long i = 0; i < countOfThreads; i++) {
            Callable<Integer> worker = createWorker(queries);
            Future<Integer> submit = executor.submit(worker);
            tasks.add(submit);
        }

        for (Future<Integer> task : tasks) {
                totalCheck += task.get();
        }

        executor.shutdown();

        Assert.assertEquals(totalCheck.intValue(), countOfThreads * queries.size());

    }


    private Callable<Integer> createWorker(final Map<String, String> queries) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int succeed = 0;
                for (Map.Entry<String, String> entry : queries.entrySet()) {
                    Assert.assertEquals(pipelineBuilder.serialize(pipelineBuilder.buildAggregation(entry.getKey())), entry.getValue());
                    succeed++;
                }
                return succeed;
            }
        };
    }

}
