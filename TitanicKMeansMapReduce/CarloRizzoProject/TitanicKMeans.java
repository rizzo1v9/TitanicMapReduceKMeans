import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TitanicKMeans{

	public static double centers[][] = {{0.42424242, 0.58441558, 14.64575758},
 						{ 0.37162162,  0.67567568, 51.20945946},
 						{0.36914062,  0.66796875, 30.27293716}};
 	public static double centers1[] = {0.42424242, 0.58441558, 14.64575758,
 						 0.37162162,  0.67567568, 51.20945946,
 						0.36914062,  0.66796875, 30.27293716};
 						
 						public static double instance[][] = {{12.52380952, 4.67559524, 38.1743447},
				       {74.42343495, 9.32472932, 40.8318328},
				       {84.3131345, 3.89128388, 42.39923923}};
				       

 	
  public static class Map extends Mapper<LongWritable, Text, LongWritable, Text> {
	
	private Text Survived = new Text();
	
	//private DoubleWritable Percent = new DoubleWritable();
	
	
	public static double instance1[] = {12.52380952, 4.67559524, 38.1743447,
				       74.42343495, 9.32472932, 40.8318328,
				       84.3131345, 3.89128388, 42.3992392};

	
public double computeDist(double[][] instance, double[][] centers) {


    double ans = 0;
    for (int i = 0; i < instance.length; i++) {
        for (int j = 0; j < instance[i].length; j++) {
            if (instance[i][j] >= centers[i][j]) {
                ans = Math.sqrt((Math.pow((instance[i][j] - centers[i][j]), 2)) + Math.pow((instance[i + 1][j + 1] - centers[i + 1][j + 1]), 2));
                return ans;
            } else if (instance[i][j] <= centers[i][j]) {
                ans = Math.sqrt((Math.pow((centers[i][j] - instance[i][j]), 2)) + Math.pow((centers[i + 1][j + 1] - instance[i + 1][j + 1]), 2));
                return ans;
            }


        }
    }
       return ans;
}
public void map(LongWritable key, Text values, Context context) throws IOException, InterruptedException {

    String s = values.toString();
    
    String str[] = s.split(", ");
    
    double num;
    
    double minDis = (51.20945946 * 2);
    int index = -1;
    double tmp;
    for (int i = 0; i < centers.length; i++) {
        
            
        Arrays.toString(instance[i]);
        Arrays.toString(centers[i]);

        double dis = computeDist(instance, centers);
        
        for (int j = 0; j < instance.length ; j++) {

            if (dis < minDis)
            {
                minDis = dis;
                
                index = i;
            }
        }
    }
        //String values1 = Arrays.deepToString(centers);
        String values1 = Arrays.deepToString(centers);

        //System.out.println(index);
        //System.out.println(values1);
        Survived.set(values1);
        //String k = String.valueOf(index);
        key.set(index);
    
        context.write(key, Survived);

    }
}
	
  public static class Reduce extends Reducer<LongWritable, Text, LongWritable, Text> {
	
	private Text Survived = new Text();
	
		//DoubleWritable outval = new  DoubleWritable();
	public void reduce(LongWritable key, Text values, Context context)
            throws IOException, InterruptedException
        {
        
    		
		double centersi[][] = centers;
		
		
		int num = 0;
		for(int j = 0; j < centers.length; j++){
			num++;
			}
			for(int i = 0; i < centersi.length; i++){
			 for (int k = 0; k < instance[i].length; k++){
				centersi[i][k] = centersi[i][k] / (double) num;
				}
			String values2 = (Arrays.deepToString(centers));
			Survived.set(values2);
			context.write(key, Survived);
			
			
		}
	}
	}
	
	  public static void main(String[] args) throws Exception {
    		Configuration conf = new Configuration();
    	Job job = Job.getInstance(conf, "SSA");
    	job.setJarByClass(TitanicKMeans.class);
    	job.setMapperClass(Map.class);
    	job.setReducerClass(Reduce.class);
    	job.setOutputKeyClass(LongWritable.class);
    	job.setOutputValueClass(Text.class);
    	FileInputFormat.addInputPath(job, new Path(args[0]));
    	FileOutputFormat.setOutputPath(job, new Path(args[1]));
    	System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
		
		
			
