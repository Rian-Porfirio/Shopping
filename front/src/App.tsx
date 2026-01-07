import { useEffect, useRef, useState } from "react";
import type { Product } from "./domain/Product";
import axios from "axios";
import { Button } from "primereact/button";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { InputText } from "primereact/inputtext";
import { InputNumber } from "primereact/inputnumber";
import { Dialog } from "primereact/dialog";
import { Toast } from "primereact/toast";
import { ConfirmDialog, confirmDialog } from "primereact/confirmdialog";

const API_URL = "http://localhost:8080/api/products";

export default function App() {
  const [produtos, setProdutos] = useState<Product[]>([]);
  const [visible, setVisible] = useState(false);
  const [produto, setProduto] = useState<Product>({
    nome: "",
    categoria: "",
    valor: 0,
  });

  const toast = useRef<Toast>(null);

  const carregar = async () => {
    const res = await axios.get(API_URL);
    setProdutos(res.data);
  };

  useEffect(() => {
    carregar();
  }, []);

  const abrirNovo = () => {
    setProduto({ nome: "", categoria: "", valor: 0 });
    setVisible(true);
  };

  const editar = (p: Product) => {
    setProduto({ ...p });
    setVisible(true);
  };

  const isFormularioValido = () => {
    return (
      produto.nome.trim().length > 0 &&
      produto.categoria.trim().length > 0 &&
      produto.valor > 0
    );
  };

  const salvar = async () => {
    if (!isFormularioValido()) {
      toast.current?.show({
        severity: "error",
        summary: "Erro",
        detail: "Erro ao salvar o produto. Verifique os campos.",
      });
      return;
    }

    try {
      if (produto.id) {
        await axios.put(`${API_URL}/${produto.id}`, produto);
        toast.current?.show({
          severity: "success",
          summary: "Produto atualizado",
          detail: "O produto foi atualizado com sucesso",
        });
      } else {
        await axios.post(API_URL, produto);
        toast.current?.show({
          severity: "success",
          summary: "Produto cadastrado",
          detail: "O produto foi cadastrado com sucesso",
        });
      }

      setVisible(false);
      carregar();
    } catch {
      toast.current?.show({
        severity: "error",
        summary: "Erro",
        detail: "Erro ao salvar o produto",
      });
    }
  };

  const confirmarExclusao = (id?: number) => {
    confirmDialog({
      message: "Deseja realmente excluir este produto?",
      header: "Confirmação",
      icon: "pi pi-exclamation-triangle",
      acceptLabel: "Sim",
      rejectLabel: "Não",
      accept: async () => {
        try {
          await axios.delete(`${API_URL}/${id}`);
          toast.current?.show({
            severity: "success",
            summary: "Produto excluído",
            detail: "O produto foi removido com sucesso",
          });
          carregar();
        } catch {
          toast.current?.show({
            severity: "error",
            summary: "Erro",
            detail: "Erro ao excluir o produto",
          });
        }
      },
    });
  };

  return (
    <div style={{ minHeight: "100vh" }}>
      <Toast ref={toast} />
      <ConfirmDialog />

      <div className="w-7">
        <div className="flex justify-content-between align-items-center mb-3">
          <h2>Produtos</h2>
          <Button
            label="Adicionar"
            icon="pi pi-plus"
            onClick={abrirNovo}
            className="btn-adicionar"
          />
        </div>

        <DataTable
          value={produtos}
          responsiveLayout="scroll"
          className="datatable-produtos"
        >
          <Column field="nome" header="Nome" />
          <Column field="categoria" header="Categoria" />
          <Column field="valor" header="Valor" />

          <Column
            header="Ações"
            body={(row: Product) => (
              <div className="flex gap-2">
                <Button
                  icon="pi pi-pencil"
                  className="p-button-text btn-editar"
                  onClick={() => editar(row)}
                />
                <Button
                  icon="pi pi-trash"
                  className="p-button-text p-button-danger btn-excluir"
                  onClick={() => confirmarExclusao(row.id)}
                />
              </div>
            )}
          />
        </DataTable>
      </div>

      <Dialog
        header={produto.id ? "Editar Produto" : "Novo Produto"}
        visible={visible}
        onHide={() => setVisible(false)}
        modal
        style={{ width: "fit-content" }}
        contentStyle={{ padding: "15px" }}
      >
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            gap: "10px",
            minWidth: "500px",
          }}
        >
          <InputText
            placeholder="Nome Produto"
            className="w-full input-nome"
            value={produto.nome}
            onChange={(e) => setProduto({ ...produto, nome: e.target.value })}
          />

          <InputText
            placeholder="Categoria"
            className="w-full input-categoria"
            value={produto.categoria}
            onChange={(e) =>
              setProduto({ ...produto, categoria: e.target.value })
            }
          />

          <div className="p-inputgroup flex-1">
            <span className="p-inputgroup-addon">R$</span>
            <InputNumber
              className="w-full input-valor"
              value={produto.valor}
              onValueChange={(e) =>
                setProduto({ ...produto, valor: e.value || 0 })
              }
            />
            <span className="p-inputgroup-addon">.00</span>
          </div>

          <Button
            label="Salvar"
            className="mt-2 btn-salvar"
            style={{ width: "100%" }}
            onClick={salvar}
          />
        </div>
      </Dialog>
    </div>
  );
}
