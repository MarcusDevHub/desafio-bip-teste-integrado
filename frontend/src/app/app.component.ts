import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BeneficioService } from './services/beneficio.service';
import { Beneficio } from './models/beneficio';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  beneficios: Beneficio[] = [];
  carregando = false;
  erro = '';
  title = 'frontend';

  constructor(private beneficioService: BeneficioService) { }

  ngOnInit(): void {
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    this.carregando = true;
    this.erro = '';

    this.beneficioService.listar().subscribe({
      next: (dados) => {
        this.beneficios = dados;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Erro ao carregar benefícios.';
        this.carregando = false;
      }
    });
  }
}
