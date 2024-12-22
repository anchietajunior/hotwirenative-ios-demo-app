import HotwireNative
import UIKit

final class ButtonComponent: BridgeComponent {

  // É importante termos esta string nomeando nosso component,
  // um stimulus controller vai ser conectar com esta component
  // através desta string.
  override class var name: String { "button" }

  // No evento connect do Stimulus, o component receberá
  // uma mensagem com as informações necessárias para o botão
  // como o título.
  override func onReceive(message: Message) {
      print(message)
      // Caso queira ver o que está chegando no component
      // vindo do Rails, pode colocar um print aqui desta forma:
      // print(message), assim o conteúdo da message aparecerá
      // nos logs do XCode.
      guard let viewController else { return }
      addButton(via: message, to: viewController)
  }

  private var viewController: UIViewController? {
    delegate.destination as? UIViewController
}

  // Esta função será responsável por montar e renderizar o botão
  // nativo na tela do app iOS.
  // Faremos um reply para a função connect do Stimulus controller
  // e explicitamente adicionar um botão na Navbar ao lado direito.
  private func addButton(via message: Message, to viewController: UIViewController) {

    guard let data: MessageData = message.data() else { return }

    let action = UIAction { [unowned self] _ in
      self.reply(to: "connect")
    }

    let item = UIBarButtonItem(title: data.title, primaryAction: action)
    viewController.navigationItem.rightBarButtonItem = item
  }
}

// Decodificamos a mensagem recebido do Stimulus Controller
// para uma Struct chamada MessageData, na sessão sobre Swift
// falamos um pouco sobre class, struct e extension.
private extension ButtonComponent {
  struct MessageData: Decodable {
    let title: String
  }
}
