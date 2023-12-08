package com.example.transportsapi.controllers;


import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.RoleModel;
import com.example.transportsapi.service.CityService;
import com.example.transportsapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleModel> getAll(){
        return roleService.getAll();
    }

    @PostMapping
    public RoleModel create(@RequestBody RoleModel role){
        return roleService.createOrUpdate(role);
    }

    @PutMapping("/{id}")
    public RoleModel update(@RequestBody RoleModel role, @PathVariable Long id){
        role.setId(id);
        return roleService.createOrUpdate(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        roleService.delete(id);
    }
}
