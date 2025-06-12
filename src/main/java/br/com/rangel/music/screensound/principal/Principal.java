package br.com.rangel.music.screensound.principal;

import br.com.rangel.music.screensound.model.Artista;
import br.com.rangel.music.screensound.model.Musica;
import br.com.rangel.music.screensound.model.TipoArtista;
import br.com.rangel.music.screensound.repository.ArtistaRepository;
import br.com.rangel.music.screensound.service.ConsultaGemini;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final ArtistaRepository repository;
    private Scanner sc = new Scanner(System.in);

    public Principal(ArtistaRepository repository) {
        this.repository = repository;
    }

    public void exibirMenu() {
        var opcao = -1;
        while(opcao != 9) {
            var menu = """
                    *** Screeen Sound Musicas ***
                    
                    1 - Cadastrar artista
                    2 - Cadastrar musicas
                    3 - Listar musicas
                    4 - Buscar musicas por artista
                    5 - Pesquisar dados sobre um artista
                    
                    9 - Sair
                    """;
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    cadastraArtista();
                    break;
                case 2:
                    cadastraMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscaMusicaPorArtista();
                    break;
                case 5:
                    pesquisaDadosSobreArtista();
                    break;
                case 9:
                    System.out.println("Encerrando programa!");
                    break;
                default:
                    System.out.println("Opção Invalida tente novamente!");
            }

        }


    }

    private void cadastraArtista() {
        var cadastraNovo = "S";

        while(cadastraNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do artista: ");
            var nome = sc.nextLine();
            System.out.println("Informe o tipo desse artista (solo,dupla ou banda) ");
            var tipo = sc.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repository.save(artista);
            System.out.println("Voce uer cadastrar um novo artista (S/N)? ");
            cadastraNovo = sc.nextLine();
        }
    }

    private void cadastraMusicas() {
        System.out.println("Cadastra musica de qual artista? ");
        var nome = sc.nextLine();
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);
        if(artista.isPresent()) {
            System.out.println("Digite o nome da musica: ");
            var nomeMusica = sc.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repository.save(artista.get());
        }else {
            System.out.println("Artista não encontrado!");
        }
    }

    private void listarMusicas() {
        List<Artista> artista = repository.findAll();
        artista.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscaMusicaPorArtista() {
        System.out.println("Digite o nome do artista para buscar as musicas");
        var nomeArtista = sc.nextLine();
        List<Musica> musicasArtista = repository.buscaMusicaPorArtista(nomeArtista);
        musicasArtista.forEach(System.out::println);
    }

    private void pesquisaDadosSobreArtista() {
        System.out.println("Pesquisar Dados sobre qual artista? ");
        var nome = sc.nextLine();
        var resposta = ConsultaGemini.obterInformacao(nome);
        System.out.println(resposta.trim());
    }


}
