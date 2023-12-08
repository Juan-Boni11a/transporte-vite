package com.example.transportsapi.service;

import com.example.transportsapi.models.RoleModel;
import com.example.transportsapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleModel> getAll() {
        return roleRepository.findAll();
    }

    public RoleModel createOrUpdate(RoleModel role) {
        return roleRepository.save(role);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
