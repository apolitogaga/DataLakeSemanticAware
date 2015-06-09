package dataLake;

import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.*;
import dataAmazonType.*;

public class Integrator {
	private ProcessReviews proc;
	private static HashMap<String, Movie> movies;
	private static HashMap<String, User> reviewers;
	private static HashMap<String, String> films;
	private SPARQLManager test;

	public Integrator() {
		proc = new ProcessReviews();
		LoadXML.parseXML(proc);
		movies = proc.getMovies();
		reviewers = proc.getReviewers();
		test = new SPARQLManager();
		films = new HashMap<String, String>();
	}

	public void integrate() {
		System.out.println("ExactMatch");
		exactMatch();
		System.out.println("Jaccard");
//		jaccard();
		System.out.println("IntegrateAll");
		insertAmazon();

	}

	public void jaccard() {
		ArrayList<ArrayList<String>> res = test.selectMovNames();
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> literals = res.get(1);
		ArrayList<String> toDelete= new ArrayList<String>();
		Double jacIndex = (double) -1;
		int w = 0;
		//
		jacIndex = Jaccard.jaccardCoeffecient("ssssss", "ssagss");
		System.out.println("\n Jaccard Test: \t\t " + Double.toString(jacIndex));
		for (String key : movies.keySet()) {
			w = w++;

			for (int i = 1; i < literals.size(); i++) {

				jacIndex = Jaccard.jaccardCoeffecient(key, subjects.get(i));
				// System.out.print(key+" "+ literals.get(i) +
				// Double.toString(jacIndex));
				if (jacIndex > .7) {
					System.out.println("\n\t\t" + key + " <<<>>>> "
							+ literals.get(i) + Double.toString(jacIndex));
					Movie mov = movies.get(key);
					Double average = mov.getAverage();
					String av = Double.toString(average);
					test.insertRatingInstance(av + "");
					test.insertMovieRating(subjects.get(0), av + "");
					toDelete.add(key);
				}
			}
			try {
			} catch (Exception e) {
				continue;/* ignore */
			}
		}
		deleteMovies(toDelete);
	}

	public void insertAmazon() {
		int count = 0;
		int error =0;
		for (String key : movies.keySet()) {
			count++;
			Movie mov = movies.get(key);
			String title = mov.getName();
			Double average = mov.getAverage();
			String av = Double.toString(average);
			test.insertMovieAmazon(count + "");
			try{test.insertMovieAmazonName(title, count + "");}catch(Exception e ){
				System.out.println(e);error++;continue;}
			test.insertMovieAmazonRating(count+"", av + "");
			
		}
		System.out.println("Errors " + error);
	}

	public void exactMatch() {
		ArrayList<ArrayList<String>> res2;
		ArrayList<String> subFromTitles = null;
		ArrayList<String> litFromTitles = null;
		Double average = (double) 0;
		int errors = 0;
		ArrayList<String> toDelete= new ArrayList<String>();
		Movie mov;
		for (String key : movies.keySet()) {
			try {
				res2 = test.selectMovieFromTitle(key);
				subFromTitles = res2.get(0);
				litFromTitles = res2.get(1);
			} catch (Exception e) {
				errors++;
				continue;/* ignore */
			}
			;
			if (litFromTitles.size() > 0) {
//				mov = movies.get(key);
//				average = mov.getAverage();
//				String av = Double.toString(average);
//				test.insertRatingInstance(av + "");
//				test.insertMovieRating(subFromTitles.get(0), av + "");
				toDelete.add(key);
			}
		}
		deleteMovies(toDelete);
		System.out.println("Errors " + errors);
	}
	public void deleteMovies(ArrayList<String> toDelete){
		for(String key:toDelete)
			movies.remove(key);
	}
}
