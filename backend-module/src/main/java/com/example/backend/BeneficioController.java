package com.example.backend;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@CrossOrigin(origins = "http://localhost:4200")

public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Beneficio> list() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Beneficio getById(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Beneficio create(@RequestBody Beneficio beneficio) {
        return service.criar(beneficio);
    }

    @PutMapping("/{id}")
    public Beneficio update(@PathVariable Long id, @RequestBody Beneficio beneficio) {
        return service.atualizar(id, beneficio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletar(id);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest request) {
        service.transferir(request.getFromId(), request.getToId(), request.getAmount());
    }

}
