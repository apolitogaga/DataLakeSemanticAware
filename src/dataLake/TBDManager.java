package dataLake;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.*;

public class TBDManager {
	private Dataset datalake;

	private String directory = "DataLake";
	public Model tdb;

	public void execUpdate(String sparqlUpdateString) {
		UpdateRequest request = UpdateFactory.create(sparqlUpdateString);
		UpdateAction.execute(request, tdb);
		// proc.execute();
	}

	public void updateTriple(String sparqlUpdateString) {
		datalake.begin(ReadWrite.WRITE);
		try {
			GraphStore graphStore = GraphStoreFactory.create(datalake);
			execUpdate(sparqlUpdateString);
			datalake.commit();

		} finally {
			datalake.end();
		}
	}

	public TBDManager() {
		this.datalake = TDBFactory.createDataset(directory);
		tdb = datalake.getDefaultModel();
	}

	public void create() {
		try {
			// must add the metadata thing around here somewhere

			// create TBD dataset
			this.datalake = TDBFactory.createDataset(directory);

			tdb = datalake.getDefaultModel();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	public Model store(String query) {
		Query qResult = QueryFactory.create(query);
		datalake.begin(ReadWrite.WRITE);
		Model result;
		QueryExecution qexec = QueryExecutionFactory.create(qResult, tdb);
		// model.add(qResult, service); // would be the best way to use this
		// query but there's an error while executing it.
		// tbd.add(result);
		result = qexec.execConstruct(tdb);
		tdb.commit();
		datalake.commit();
		return result;
	}

	public Model store(Query query) {
		return tdb;
	} // /deleeeeteeeeeeee

	public void queryTBD() {
		System.out.println("Query this shite");
		String qs1 = " PREFIX dbpedia: <http://dbpedia.org/ontology/> PREFIX movie: <http://data.linkedmdb.org/resource/movie/> PREFIX dc: <http://purl.org/dc/terms/> "
				+ "SELECT * {?s a dbpedia:Film}";

		try (QueryExecution qExec = QueryExecutionFactory.create(qs1, datalake)) {
			ResultSet rs = qExec.execSelect();
			ResultSetFormatter.out(rs);
		}
	}

	public Model execConstQuery(String query) {
		Query qResult = QueryFactory.create(query);
		// would much rather get rid of this sentence but I couldn't find
		// another way around it
		QueryExecution qexec = QueryExecutionFactory.create(qResult, tdb);
		return qexec.execConstruct();
	}

	public Model execConstQuery(Query query) {
		return tdb;
	}// /deleeeeteeeeeeee

	public ArrayList<ArrayList<String>> getSelectQuery(String query,
			String subject, String predicate, String object) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		ArrayList<String> subjects = new ArrayList<String>();
		ArrayList<String> predicates = new ArrayList<String>();
		ArrayList<String> objects = new ArrayList<String>();
		ArrayList<String> literals = new ArrayList<String>();
		Query qResult = QueryFactory.create(query);
		Resource rsu = null;
		Resource rpr = null;
		Resource rob = null;
		Literal lit = null;
		try (QueryExecution qExec = QueryExecutionFactory.create(qResult, tdb)) {
			ResultSet resSet = qExec.execSelect();
			while (resSet.hasNext()) {
				QuerySolution soln = resSet.nextSolution();
				rsu = soln.getResource(subject);
				try {
					rpr = soln.getResource(predicate);
				} catch (Exception e) {/* ignore */
				}
				;
				try {
					rob = soln.getResource(object);
				} catch (Exception e) {/* ignore */
				}
				;
				try {
					lit = soln.getLiteral(object);
				} catch (Exception e) {/* ignore */
				}
				;
				try {
					subjects.add(rsu.getURI());
				} catch (Exception e) {/* ignore */
				}
				;
				try {
					predicates.add(rpr.getURI());
				} catch (Exception e) {/* ignore */
				}
				;
				try {
					objects.add(rob.getURI());
				} catch (Exception e) {/* ignore */
				}
				;
				try {
					literals.add(lit.getValue().toString());
				} catch (Exception e) {/* ignore */
				}
				;
			}
		}
		res.add(subjects);
		res.add(predicates);
		res.add(objects);
		res.add(literals);
		return res;
	}

	public void selectQuery(String squery, String subject) {
		Query qResult = QueryFactory.create(squery);
		try (QueryExecution qExec = QueryExecutionFactory.create(qResult, tdb)) {
			ResultSet rs = qExec.execSelect();
			while (rs.hasNext()) {
				QuerySolution soln = rs.nextSolution();
				Resource r = soln.getResource("?subject");
				System.out.println(r.toString());
			}
		}
	}

	private void writeFileDB(Model results, String format) {
		FileWriter fostream;
		try {
			fostream = new FileWriter(LoadXML.DATA_DIRECTORY
					+ "dbpediafilmdataset.nt");
			BufferedWriter out = new BufferedWriter(fostream);
			results.write(out, format);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeFileDB(Model results) {
		writeFileDB(results, "TURTLE");
	}

	public Model getModel() {
		return datalake.getDefaultModel();
	}

	public void read() {
		datalake.begin(ReadWrite.READ);
		Model model = datalake.getDefaultModel();
		System.out.println(model.listSubjects().toList());

		List<Resource> subjects = model.listSubjects().toList();
		System.out.println(subjects.size());
		for (int i = 0; i < subjects.size(); i++) {
			// System.out.println(subjects.get(i).toString() );
			System.out.println(subjects.get(i).getURI());

		}
		datalake.end();
	}

	public void close() {
		if (tdb != null && datalake != null) {
			tdb.commit();
			tdb.close();
			datalake.close();
		}
	}

	/**
	 * save the given model
	 * 
	 * @param model
	 */
	public void saveModel(Model model) {
		if (tdb != null && datalake != null) {
			tdb.commit();
			tdb.close();
			datalake.close();
		}
	}
}
