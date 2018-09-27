package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import model.Resume;



@Transactional
public interface ResumeRepository extends JpaRepository<Resume, Long>{	
	public Resume findByName(String name);
}