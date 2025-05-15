package main.natationtn.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    public Pageable createPageRequest(int page , int size , String sortField, String sortDirection)
    {
        return PageRequest.of(page, size, sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortField).descending() : Sort.by(sortField).ascending());
    }

    public <T> Page<T> getPaginatedData(Pageable pageable, PagingAndSortingRepository<T, Long> repository) {
        return repository.findAll(pageable);
    }
}
