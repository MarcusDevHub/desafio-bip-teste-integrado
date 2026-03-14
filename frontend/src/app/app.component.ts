import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from './services/beneficio.service';
import { Beneficio } from './models/beneficio';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  beneficios: Beneficio[] = [];
  carregando = false;
  erro = '';

  novoBeneficio = {
    nome: '',
    descricao: '',
    valor: 0,
    ativo: true
  };

  transferencia = {
    fromId: 0,
    toId: 0,
    amount: 0
  };

  mensagemSucesso = '';


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

  criarBeneficio(): void {
    this.beneficioService.criar(this.novoBeneficio).subscribe({
      next: () => {
        this.novoBeneficio = {
          nome: '',
          descricao: '',
          valor: 0,
          ativo: true
        };
        this.carregarBeneficios();
      },
      error: () => {
        this.erro = 'Erro ao criar benefício.';
      }
    });
  }
  transferirValor(): void {
    this.erro = '';
    this.mensagemSucesso = '';

    if (this.transferencia.fromId === this.transferencia.toId) {
      this.erro = 'Origem e destino não podem ser o mesmo benefício.';
      return;
    }

    this.beneficioService.transferir(this.transferencia).subscribe({
      next: () => {
        this.mensagemSucesso = 'Transferência realizada com sucesso.';
        this.transferencia = {
          fromId: 0,
          toId: 0,
          amount: 0
        };
        this.carregarBeneficios();
      },
      error: (err) => {
        this.erro = err?.error?.message || 'Erro ao realizar transferência.';
      }

    });
  }

  deletarBeneficio(id: number): void {
    this.erro = '';
    this.mensagemSucesso = '';

    const confirmar = window.confirm('Tem certeza que deseja excluir este benefício?');

    if (!confirmar) {
      return;
    }

    this.beneficioService.deletar(id).subscribe({
      next: () => {
        this.mensagemSucesso = 'Benefício excluído com sucesso.';
        this.carregarBeneficios();
      },
      error: (err) => {
        this.erro = err?.error?.message || 'Erro ao excluir benefício.';
      }
    });
  }


}
