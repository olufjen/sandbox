/*
 *  How to use WEKA API in Java 
 *  Copyright (C) 2014 
 *  @author Dr Noureddin M. Sadawi (noureddin.sadawi@gmail.com)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it as you wish ... 
 *  I ask you only, as a professional courtesy, to cite my name, web page 
 *  and my YouTube Channel!
 *  
 */

package no.games.samples;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.evaluation.ConfusionMatrix;
import weka.classifiers.evaluation.output.prediction.PlainText;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;

import java.awt.BorderLayout;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
//import java.io.FileReader;
import java.util.Random;

import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
public class LoadSaveData{
	public static void main(String args[]) throws Exception{
		DataSource source = new DataSource("C:\\ullern\\weka\\multi_instance\\weather-nominal.arff");
/*
 * These instances are different !!		
 */
		Instances dataset = source.getDataSet();
		Instances trainingSet = source.getDataSet();
/*
 * A FilteredClassifier and dump: THe java Object must implement Serializable		
 */
        FilteredClassifier model = new FilteredClassifier();
  
        model.setFilter(new StringToWordVector());
//        model.setClassifier(new NaiveBayesMultinomial());
        model.setClassifier(new J48()); // Only tree type classifiers can be graphed !!!
		trainingSet.setClassIndex(trainingSet.numAttributes()-1); // Må alltid settes !!
        try {
            model.buildClassifier(trainingSet);
        } catch (Exception e1) { // TODO Auto-generated catch block
            e1.printStackTrace();
        }

/* 
 * Saves a serializable object:       
 * ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(
                        "C:\\ullern\\weka\\multi_instance\\MyModel.model"));
        oos.writeObject(model);
        oos.flush();
        oos.close();*/
        
        String modelGraph = model.graph();// Only tree type classifiers can be graphed !!!
        System.out.println("Graph of model: "+modelGraph);
		//Instances dataset = new Instances(new BufferedReader(new FileReader("/home/likewise-open/ACADEMIC/csstnns/test/house.arff")));		
		String[] classNames = new String[6];
		for (int index = 0;index<5;index++) {
			Attribute attribute = dataset.attribute(index);
			String name = attribute.name();
			classNames[index] = name;
		}
		
		ConfusionMatrix matrix = new ConfusionMatrix(classNames);
		System.out.println(dataset.toSummaryString());
		System.out.println(matrix.toString());
/*		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File("C:\\ullern\\weka\\multi_instance\\newweather.arff"));
		saver.writeBatch();*/
		
		dataset.setClassIndex(dataset.numAttributes()-1); // Returns the class attribute (No of attributes - 1) Must always be set.
/*
 *  Classifiers		
 */
		/*
		 * To specify a percentage split:
		 */
		trainingSet.randomize(new java.util.Random(0));
		int trainSize = (int) Math.round(trainingSet.numInstances() * 0.8); //80% is used for training
		int testSize = trainingSet.numInstances() - trainSize;				// The rest is used for testing
		Instances train = new Instances(trainingSet, 0, trainSize);
		train.setClassIndex(train.numAttributes()-1); // Må alltid settes !!
		Instances test = new Instances(trainingSet, trainSize, testSize);
		test.setClassIndex(test.numAttributes()-1); //Må alltid settes !!
		Evaluation evaltrain = new Evaluation(train);
		ZeroR zeroClassifier = new ZeroR();
		zeroClassifier.buildClassifier(train);
		evaltrain.evaluateModel(zeroClassifier, test); // Evaluate the classifier on the test set.
		System.out.println("train classifier "+evaltrain.toSummaryString());
		System.out.println("train classifier confusion matrix "+evaltrain.toMatrixString()); // Returns baseline result
		
		ZeroR zeroR = new ZeroR();
		zeroR.buildClassifier(dataset);
		Evaluation evalzeroR = new Evaluation(dataset);
		String predict = "first-last"; // Specifies a range of values Check!! ??
		PlainText plainText = new PlainText();
		StringBuffer buffer = new StringBuffer();
		plainText.setAttributes(predict);
		plainText.setBuffer(buffer);
		Instances emptyInstances = new Instances(dataset,0);
		plainText.setHeader(emptyInstances);
		evalzeroR.crossValidateModel(zeroR, dataset, 10, new Random(1)); // This returns baseline result
//		evalzeroR.evaluateModel(zeroR, dataset);
//		evalzeroR.evaluateModel(zeroR, dataset, plainText); // This returns baseline result
		System.out.println("zeroR classifier "+evalzeroR.toSummaryString());
		System.out.println("zeroR classifier confusion matrix\n===================\n "+evalzeroR.toMatrixString());		
		NaiveBayes nb = new NaiveBayes(); // A Naive Bayes classifier
		nb.buildClassifier(dataset); // Builds and resets the classifier with the dataset
		Evaluation evalnb = new Evaluation(dataset);
		
		OneR oneR = new OneR(); // A one rule classifier
		oneR.buildClassifier(dataset);// Builds and resets the classifier with the dataset
		Evaluation evaloneR = new Evaluation(dataset);
		J48 treeClassifier = new J48(); // A J48 classifier
		treeClassifier.buildClassifier(dataset);
		Evaluation evalTree = new Evaluation(dataset);
		String tree = treeClassifier.collapseTreeTipText();
		
/*
 * In k-fold cross-validation, the original sample is randomly partitioned into k subsamples.
 * Of the k subsamples, a single subsample is retained as the validation data for testing the model,
 * and the remaining k − 1 subsamples are used as training data. The cross-validation process is then repeated k times (the folds),
 * with each of the k subsamples used exactly once as the validation data. The k results from the folds then can be averaged
 * (or otherwise combined) to produce a single estimation
 * k-fold - the number of times the cross-validation is performed.
 * Cross-validation is a systematic way of doing repeated holdout that actually improves upon it by reducing the variance of the estimate.
 * 
 * With cross-validation, we divide it just once, but we divide into,
 * say, 10 pieces. Then we take 9 of the pieces and use them for training and the last piece we use for testing.
 * Then with the same division, we take another 9 pieces and use them for
 * training and the held-out piece for testing. We do the whole thing 10 times, using a
 * different segment for testing each time. In other words, we divide the dataset into
 * 10 pieces, and then we hold out each of these pieces in turn for testing, train on the
 * rest, do the testing and average the 10 results. That would be 10-fold crossvalidation.
 * Divide the dataset into 10 parts (these are called “folds”); hold out each part in turn;
 * and average the results. So each data point in the dataset is used once for testing
 * and 9 times for training. That’s 10-fold cross-validation.
 */
		evalnb.crossValidateModel(nb, dataset, 10,new Random(1));
		evaloneR.crossValidateModel(oneR, dataset, 10,new Random(1));
		evalTree.crossValidateModel(treeClassifier, dataset, 10, new Random(1));
		   TreeVisualizer tv = new TreeVisualizer(null,
			         treeClassifier.graph(),
			         new PlaceNode2());
		
		double entropy = evalTree.priorEntropy();
		
/*		
 * Using the meta-classifier
 */
		AttributeSelectedClassifier selectClassifier = new AttributeSelectedClassifier();
		AttributeSelection attSelection = new AttributeSelection();
		CfsSubsetEval subEval = new CfsSubsetEval();
		GreedyStepwise search = new GreedyStepwise();
		search.setSearchBackwards(true);
		selectClassifier.setClassifier(treeClassifier);
		selectClassifier.setEvaluator(subEval);
		selectClassifier.setSearch(search);
		Evaluation selectEval = new Evaluation(dataset) ;
		selectEval.crossValidateModel(selectClassifier, dataset, 10, new Random(1));
		attSelection.setEvaluator(subEval);
		attSelection.setSearch(search);
		attSelection.SelectAttributes(dataset);
		int[] indices = attSelection.selectedAttributes();
		for (int i: indices) {
			Attribute attribute = dataset.attribute(i);
		}
		System.out.println("Meta classifier "+selectEval.toSummaryString());
		System.out.println("Meta classifier confusion matrix\n===================\n "+selectEval.toMatrixString());
		System.out.println("Selected attribute indices :\n"+Utils.arrayToString(indices));
		String tipText = treeClassifier.binarySplitsTipText();
		String collapsText = treeClassifier.collapseTreeTipText();
		String graf = treeClassifier.graph();
		System.out.println(" Graph "+graf);
		System.out.println(evalzeroR.toSummaryString("\nResults zero R (Baseline?)\n==================\n",true));
		System.out.println(evalzeroR.fMeasure(1)+ " "+ evalnb.precision(1)+" "+ evalnb.recall(1)); 
		System.out.println(evalzeroR.toClassDetailsString());
		System.out.println(evalzeroR.toCumulativeMarginDistributionString());
		System.out.println("zero R confusion matrix\n==================\n"+evalzeroR.toMatrixString());
		System.out.println("Naive Bayes confusion matrix\n==================\n"+evalnb.toMatrixString());
		System.out.println("One R confusion matrix\n==================\n"+evaloneR.toMatrixString());
		System.out.println("J48 confusion matrix\n==================\n"+evalTree.toMatrixString());
		System.out.println("J48 collapsed tree\n==================\n"+tree);
		System.out.println(evalnb.toSummaryString("\nResults naive bayes\n==================\n",true));
		System.out.println(evalnb.fMeasure(1)+ " "+ evalnb.precision(1)+" "+ evalnb.recall(1)); 
		System.out.println(evalnb.toClassDetailsString());
		System.out.println(evalnb.toCumulativeMarginDistributionString());
		System.out.println(evalTree.toSummaryString("\nResults J48\n==================\n",true));
		System.out.println(evalTree.fMeasure(1)+ " "+ evalTree.precision(1)+" "+ evalTree.recall(1)); 
		System.out.println(evalTree.toClassDetailsString());
		System.out.println(evalTree.toCumulativeMarginDistributionString());
		System.out.println("J48 tree: "+tv.toString());
		System.out.println(evaloneR.toSummaryString("\nResults One R\n==================\n",true));
		System.out.println(evaloneR.fMeasure(1)+ " "+ evaloneR.precision(1)+" "+ evaloneR.recall(1)); 
		System.out.println(evaloneR.toClassDetailsString());
		System.out.println(evaloneR.toCumulativeMarginDistributionString());
		String rules = oneR.toSource("One R rules\n==================\n");
		System.out.println("Rules "+rules);
		
/*		   final javax.swing.JFrame jf = 
		   new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
	     jf.setSize(500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     jf.getContentPane().add(tv, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	         jf.dispose();
	       }
	     });*/
		
	     System.out.println("==== Finish ==============");
	}
}
