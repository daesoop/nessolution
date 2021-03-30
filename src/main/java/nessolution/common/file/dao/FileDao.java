package nessolution.common.file.dao;

import nessolution.common.file.domin.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileDao extends JpaRepository<File, Long> {
}