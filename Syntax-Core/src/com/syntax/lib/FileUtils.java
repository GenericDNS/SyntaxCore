package com.syntax.lib;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileUtils {

    public static void writeFile( String fileName, String content ) throws IOException {
        writeFile( fileName, new ByteArrayInputStream( content.getBytes( StandardCharsets.UTF_8 ) ) );
    }

    public static void writeFile( String fileName, InputStream content ) throws IOException {
        writeFile( new File( fileName ), content );
    }

    public static void writeFile( File file, String content ) throws IOException {
        writeFile( file, new ByteArrayInputStream( content.getBytes( StandardCharsets.UTF_8 ) ) );
    }

    public static void writeFile( File file, InputStream content ) throws IOException {
        if ( content == null ) {
            throw new IllegalArgumentException( "content must not be null" );
        }
        if ( !file.exists() ) {
            file.createNewFile();
        }
        FileOutputStream stream = new FileOutputStream( file );
        byte[] buffer = new byte[1024];
        int length;
        while ( ( length = content.read( buffer ) ) != -1 ) {
            stream.write( buffer, 0, length );
        }
        stream.close();
        content.close();
    }

    public static String readFile( File file ) throws IOException {
        if ( !file.exists() || file.isDirectory() ) {
            throw new FileNotFoundException();
        }
        return readFile( new FileInputStream( file ) );
    }

    public static String readFile( String filename ) throws IOException {
        File file = new File( filename );
        if ( !file.exists() || file.isDirectory() ) {
            throw new FileNotFoundException();
        }
        return readFile( new FileInputStream( file ) );
    }

    public static String readFile( InputStream inputStream ) throws IOException {
        return readFile( new InputStreamReader( inputStream, StandardCharsets.UTF_8 ) );
    }

    private static String readFile( Reader reader ) throws IOException {
        BufferedReader br = new BufferedReader( reader );
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        temp = br.readLine();
        while ( temp != null ) {
            if ( stringBuilder.length() != 0 ) {
                stringBuilder.append( "\n" );
            }
            stringBuilder.append( temp );
            temp = br.readLine();
        }
        br.close();
        reader.close();
        return stringBuilder.toString();
    }

    public static void copyFile( File from, File to ) throws IOException {
        if ( !from.exists() ) {
            throw new FileNotFoundException();
        }
        if ( from.isDirectory() || to.isDirectory() ) {
            throw new FileNotFoundException();
        }
        FileInputStream fi = null;
        FileChannel in = null;
        FileOutputStream fo = null;
        FileChannel out = null;
        try {
            if ( !to.exists() ) {
                to.createNewFile();
            }
            fi = new FileInputStream( from );
            in = fi.getChannel();
            fo = new FileOutputStream( to );
            out = fo.getChannel();
            in.transferTo( 0, in.size(), out );
        } finally {
            if ( fi != null ) fi.close();
            if ( in != null ) in.close();
            if ( fo != null ) fo.close();
            if ( out != null ) out.close();
        }
    }

    public void copyFileFromResurces(String filename, File to){
        if (to.exists()) { return; }
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if(inputStream != null) {
            try {
                Files.copy(inputStream, to.toPath());
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean copyAllFiles(File dir, File location) throws IOException {
        if (!dir.exists() || !location.exists()) return false;
        File[] files = dir.listFiles();
        String filename = "";
        for (File file : files){
            filename = file.getName();
            try {
                copyFile(file, new File(location + "/" + filename));
            } catch (IOException e) {
                throw new IOException("ERROR WHIE COPING " + filename);
            }
        }
        return true;
    }
}