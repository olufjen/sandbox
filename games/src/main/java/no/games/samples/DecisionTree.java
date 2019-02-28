package no.games.samples;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.classifiers.trees.Id3;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Weka decision trees demo.
 *
 */
public class DecisionTree {
	private Instances trainingData;

	public static void main(String[] args) throws Exception {

		DecisionTree decisionTree = new DecisionTree("C:\\ullern\\weka\\multi_instance\\films.arff");
		J48 J48tree = decisionTree.trainTheTree();
		Id3 id3Tree = decisionTree.trainTheIDTree();
		// Print the resulted tree
		System.out.println("The J48 tree: "+J48tree.toString());
		System.out.println("The Id3 tree: "+id3Tree.toString());
		// Test the tree
		Instance testInstance = decisionTree.prepareTestInstance();
		int result = (int) J48tree.classifyInstance(testInstance);
		int id3result = (int) id3Tree.classifyInstance(testInstance);
		String readableResult = decisionTree.trainingData.attribute(3).value(result);
		System.out.println(" ------------ J48 ----------------------------- ");
		System.out.println("Test data               : " + testInstance);
		System.out.println("Test data classification: " + readableResult);
		
		String readableId3Result = decisionTree.trainingData.attribute(3).value(id3result);
		System.out.println(" ------------ ID3----------------------------- ");
		System.out.println("Test data               : " + testInstance);
		System.out.println("Test data classification: " + readableId3Result);
	}

	public DecisionTree(String fileName) {
		BufferedReader reader = null;
		try {
			// Read the training data
			reader = new BufferedReader(new FileReader(fileName));
			trainingData = new Instances(reader);

			// Setting class attribute
			trainingData.setClassIndex(trainingData.numAttributes() - 1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private J48 trainTheTree() {
		J48 J48tree = new J48();

		String[] options = new String[1];
		// Use unpruned tree.
		options[0] = "-U";

		try {
			J48tree.setOptions(options);
			J48tree.buildClassifier(trainingData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return J48tree;
	}
	private Id3 trainTheIDTree() {

		Id3 id3tree = new Id3(); //The original
		String[] options = new String[1];
		// Use unpruned tree.
		options[0] = "-U";

		try {
			id3tree.setOptions(options);
			id3tree.buildClassifier(trainingData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id3tree;
	}
	private Instance prepareTestInstance() {
		Instance instance = new DenseInstance(3);
		instance.setDataset(trainingData);

		instance.setValue(trainingData.attribute(0), "Europe");
		instance.setValue(trainingData.attribute(1), "no");
		instance.setValue(trainingData.attribute(2), "comedy");

		return instance;
	}
}
