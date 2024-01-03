package org.realworld;

import java.io.File;
import java.io.IOException;

/**
 * 여러 형식의 파일을 읽어서 Document 객체로 변환하는 기능을 제공하는 인터페이스
 */
public interface Importer {
    Document importFile(File file) throws IOException;
}
