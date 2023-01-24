package io.github.luaprogrammer.api.repository;

import io.github.luaprogrammer.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByPersonId(Long id);
}
