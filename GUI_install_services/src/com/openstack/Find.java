package com.openstack;
/**
 * Sample code that finds files that match the specified glob pattern.
 * For more information on what constitutes a glob pattern, see
 * https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob
 *
 * The file or directories that match the pattern are printed to
 * standard out.  The number of matches is also printed.
 *
 * When executing this application, you must put the glob pattern
 * in quotes, so the shell will not expand any wild cards:
 *              java Find . -name "*.java"
 */

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import static java.nio.file.FileVisitOption.*;
import java.util.*;

import com.openstack.Find.Finder;

public class Find {

	/*
	 * Find(Path startingDir,String pattern){ returnFilePath(startingDir,
	 * pattern);
	 * 
	 * }
	 */

	public Path returnFilePath(Path startingDir, String pattern) {
		Finder finder = new Finder(pattern);
		try {
			Files.walkFileTree(startingDir, finder);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MainWindow.showErrorMessage(e1.getMessage());
		}

		return finder.done();
	}

	public static class Finder extends SimpleFileVisitor<Path> {
		private List<Path> FoundFiles = new ArrayList<Path>();
		private final PathMatcher matcher;
		private int numMatches = 0;

		Finder(String pattern) {
			matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
		}

		// Compares the glob pattern against
		// the file or directory name.
		void find(Path file) {
			Path name = file.getFileName();
			if (name != null && matcher.matches(name)) {
				numMatches++;
				FoundFiles.add(file.getFileName());
				System.out.println("I found " + file);
			}
		}

		// Prints the total number of
		// matches to standard out.
		/*
		 * void done() { System.out.println("Matched: " + numMatches); }
		 */
		public Path done() {
			if (numMatches == 1) {
				return FoundFiles.get(0);
			} else {
				return Paths.get("NONE");
			}

		}

		// Invoke the pattern matching
		// method on each file.
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			find(file);
			return CONTINUE;
		}

		// Invoke the pattern matching
		// method on each directory.
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
			find(dir);
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			System.err.println(exc);
			return CONTINUE;
		}
	}

	static void usage() {
		System.err.println("java Find <path>" + " -name \"<glob_pattern>\"");
		System.exit(-1);
	}
}
/*
 * public static void main(String[] args)
 * 
 * throws IOException {
 * 
 * if (args.length < 3 || !args[1].equals("-name")) usage();
 * 
 * Path startingDir = Paths.get(args[0]); String pattern = args[2];
 * 
 * Finder finder = new Finder(pattern); Files.walkFileTree(startingDir, finder);
 * finder.done(); }
 * 
 * // TODO Auto-generated method stub
 * 
 * }
 */