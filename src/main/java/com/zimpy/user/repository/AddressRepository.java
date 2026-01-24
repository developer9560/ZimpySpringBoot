package com.zimpy.user.repository;

import com.zimpy.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.nio.file.LinkOption;
import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
  List<Address> findByUserId(Long userId);

  Optional<Address> findByIdAndUserId(Long id, Long userId);



  @Modifying
  @Query("update Address a set a.isDefault = false where a.user.id = :userId")
  void clearDefaultAddress(Long userId);

  @Modifying
  @Query("update Address a set a.isDefault = false where a.user.id = :userId AND a.id != :addressId")
  void setDefaultFalseForOthers(Long userId, Long addressId);

  @Modifying
  @Query("""
      SELECT a from Address a WHERE a.user.id = :userId
      AND a.deletedAt IS NULL
      AND a.isDefault = true
      """)
  Optional<Address> findDefaltAddress(Long userId);

  @Modifying
  @Query("""
         SELECT a FROM Address a
         WHERE a.user.id = :userId
           AND a.deletedAt IS NULL
           AND a.id <> :addressId
         ORDER BY a.createdAt ASC
      """)
  List<Address> findOtherActiveAddresses(Long userId, Long addressId);

  List<Address> findByUserIdAndDeletedAtIsNull(Long userId);

  Optional<Address> findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

}
